package mackerel.dontworry.budgetguide.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.domain.AccountCategory;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.budgetguide.domain.ASRatio;
import mackerel.dontworry.budgetguide.domain.Budget;
import mackerel.dontworry.budgetguide.domain.FixedEX;
import mackerel.dontworry.budgetguide.dto.*;
import mackerel.dontworry.budgetguide.repository.ASRatioRepository;
import mackerel.dontworry.budgetguide.repository.BudgetRepository;
import mackerel.dontworry.budgetguide.repository.FixedEXRepository;
import mackerel.dontworry.schedule.domain.ScheduleCategory;
import mackerel.dontworry.schedule.repository.ScheduleRepository;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BudgetGuideService {
    private final UserRepository userRepository;
    private final FixedEXRepository fixedEXRepository;
    private final BudgetRepository budgetRepository;
    private final AccountBookRepository accountBookRepository;
    private final ASRatioRepository ASRatioRepository;
    private final ScheduleRepository scheduleRepository;


    @Transactional
    public ResponseEntity<?> saveBudget(BudgetDTO requestDTO) throws    Exception {
        System.out.println(requestDTO.getBudget());
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        LocalDate current = LocalDate.now();
        budgetRepository.save(new Budget(current, user, requestDTO.getBudget()));
        return ResponseEntity.ok("budget received" + requestDTO.getBudget());
    }

    @Transactional
    public ResponseEntity<?> saveFixedCategory(FixedExDTO requestDTO) throws Exception{
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        String fixedList = requestDTO.getFixedEXsAsString();
        System.out.println("fixedlist: " + fixedList);
        fixedEXRepository.save(new FixedEX(user, fixedList));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    public ResponseEntity<?> allocateAccountBookBudget(String email) throws Exception{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + email));
        LocalDate current = LocalDate.now();

        //전체 예산 - 고정지출 - 유동지출 - 절약
        //예산 테이블에서 전체 예산 불러오기 -> 이번달 예산 없으면 로직 끝
        Long currentBudget = budgetRepository.findByDateAndUser(
                current.withDayOfMonth(1), current.withDayOfMonth(current.lengthOfMonth()), user.getUserId());
        if(currentBudget == null)
            return ResponseEntity.ok().build();

        //챌린지 테이블에서 한달 절약 금액 가져오기 + 전체 예산에서 절약금액 뺌 -> scaled예산으로 변경
        Long saving = Long.valueOf(170000);//if로 처리 -> 없으면 0으로 고정
        Long scaledBudget = currentBudget-saving;

        //이전 달 정보: 이전 달 / 이전 달 예산 / 이전 달 카테고리별 금액 총합(카테고리명-금액 합)
        LocalDate lastMonth = current.minusMonths(1);
        Long lastBudget = budgetRepository.findByDateAndUser(
                lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()), user.getUserId());
        List<Object[]> lastCategoryCostList = accountBookRepository.findCategoryCostSumByUser(
                lastMonth.withDayOfMonth(1).atStartOfDay(), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()).atTime(LocalTime.MAX),user.getUserId());

        // 고정 지출
        // 이전 달에 고정지출 애들 카테고리명-금액(repository에서반환) + 총액,고정지출결과리스트 반환 (고정금액이라 가격변동x) -> 메서드분리
        Long currentFixedTotal = Long.valueOf(0); //지난 달 고정지출 총액 -> 이번 달 고정 지출 총액
        List<FixedBudgetResponseDTO> onlyFixedCategoryCostList = new ArrayList<>();
        if(fixedEXRepository.findByUser(email) != null) {
            String[] fixedList = fixedEXRepository.findByUser(email).split(","); //고정지출(FIEXDEX)에서 문자열 분리해서 고정지출인애들 리스트업
            Set<String> categorySet = new HashSet<>(Arrays.asList(fixedList));
            for (Object[] result : lastCategoryCostList) {
                if(categorySet.contains(result[0])) {
                    currentFixedTotal += (Long) result[1];
                    FixedBudgetResponseDTO fixedBudget = new FixedBudgetResponseDTO((AccountCategory) result[0], (Long)result[1]);
                    onlyFixedCategoryCostList.add(fixedBudget);
                }
            }
        }

        //변동 지출
        //가계부테이블에서 고정지출을 제외한 카테고리를 가진 데이터에 대해(변동지출 카테고리) 변동 예산에 대한 각 카테고리의 비율이 어떻게 되는지 보고
        Long currentVariableTotal = scaledBudget - currentFixedTotal;
        Long lastVariableTotal = lastBudget -saving - currentFixedTotal;
        List<VariableBudgetResponseDTO> onlyVariableCategoryCostList = getVariableBudgetResponse(currentVariableTotal, current, user, lastVariableTotal);

        BudgetAllocationResponseDTO budgetAllocationDTO = new BudgetAllocationResponseDTO(currentBudget, saving, currentFixedTotal, currentVariableTotal, onlyFixedCategoryCostList, onlyVariableCategoryCostList);

        return ResponseEntity.status(HttpStatus.OK).body(budgetAllocationDTO);
    }


    @Transactional
    private String createASRatio(Object[] entry, Long lastTotal){
        String category = (String) entry[0];
        int percent = (int) (((Long) entry[1] / lastTotal) * Long.valueOf(100));
        String categoryPercent = category + "," + percent;
        return categoryPercent;
    }


    @Transactional
    private List<VariableBudgetResponseDTO> getVariableBudgetResponse(Long currentVariableTotal, LocalDate current, User user, Long lastVariableTotal){
        //가계부테이블에서 고정지출을 제외한 카테고리를 가진 데이터에 대해(유동지출 카테고리) 변동 예산에 대한 각 카테고리의 비율이 어떻게 되는지 보고
        List<VariableBudgetResponseDTO> onlyVariableCategoryCostList = new ArrayList<>();
        List<String[]> result = ASRatioRepository.findASByUserAndDate(current.withDayOfMonth(1), current.withDayOfMonth(current.lengthOfMonth()), user.getUserId(), 1);
        if(result != null && result.get(0).length == 4){//first: FOOD,40 second: TRANSPORTATION,30
            String[] resultArray = result.get(0);
            for (int i = 0; i < 4; i++) {
                String[] value = resultArray[i].toString().split(",");//0:FOOD 1:40(%)
                VariableBudgetResponseDTO variableBudget = new VariableBudgetResponseDTO(
                        AccountCategory.valueOf(value[0]), (Double)(currentVariableTotal*(Double.parseDouble(value[1])/100)), Integer.parseInt((String) value[1]));
                onlyVariableCategoryCostList.add(variableBudget);
            }
        } else {
            //ABRatio에 이번 거(지난 달) 저장 ->findTop4CategoriesWithinMonth
            LocalDate lastMonth = current.minusMonths(1);
            List<Object[]> top4CategoryCostList = accountBookRepository.findTop4CategoriesWithinMonth(
                    lastMonth.withDayOfMonth(1).atStartOfDay(), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()).atTime(LocalTime.MAX), user.getUserId());
            String[] fstf1234 = new String[4];

            for (int i = 0; i < top4CategoryCostList.size(); i++) {
                Object[] entry = top4CategoryCostList.get(i);
                fstf1234[i] = createASRatio(entry, lastVariableTotal);//이전달 변동자산 total
            }
            ASRatioRepository.save(new ASRatio(user, fstf1234[0], fstf1234[1], fstf1234[2], fstf1234[3], 1));

            //유동지출 카테고리 4개에 비율에 맞게 금액을 할당
            //유동지출 카테고리명 - 각 카테고리의 퍼센티지 - 각 카테고리에 할당된 금액 + 변동지출 총액액을 반환
            //카테고리명-변동예산에대해비율에맞게각각할당된금액-총액반환
            for (int i = 0; i < 4; i++) {
                String[] value = fstf1234[i].split(",");//0:FOOD 1:40(%)
                VariableBudgetResponseDTO variableBudget = new VariableBudgetResponseDTO(AccountCategory.valueOf(value[0]), (Double) (currentVariableTotal * (Double.parseDouble(value[1]) / 100)), Integer.parseInt(value[1]));
                onlyVariableCategoryCostList.add(variableBudget);
            }
        }
        return onlyVariableCategoryCostList;
    }


    //가계부/일정 카테고리 비율 변동
    //비율이 줄어들면 +된 절약금액을 반영해서 새로운 분배 금액 + 늘어난 절약 금액을 ...
    @Transactional
    public ResponseEntity<?> updateCategoryRatio(CategoryRatioUpdateRequestDTO requestDTO) throws Exception {
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        LocalDate current = LocalDate.now();

        int ratio_type;
        if(requestDTO.getVariableBudgetResponseDTOS() != null && requestDTO.getCurrentVariableTotal() != null) {
            ratio_type = 1; // account book
        }
        else {
            ratio_type = 2; //schedule
        }

        ASRatio currentASRatio = ASRatioRepository.findEntityByUserAndDate(current.withDayOfMonth(1), current.withDayOfMonth(current.lengthOfMonth()), user.getUserId(), ratio_type);
        if (currentASRatio != null) {//first: "FOOD,40" second: "TRANSPORTATION,30"

            //update first
            String[] value = currentASRatio.getFirst().split(",");
            if (requestDTO.getFirstCategory().equals(value[0]))//카테고리명 일치하는지 확인
                if (requestDTO.getFirstPercent() != Integer.parseInt(value[1]))//해당 카테고리에 비율 변동 있는지 확인 -> 변동 있을 때 수정
                    currentASRatio.updateFirst(requestDTO.getFirstCategory() + "," + requestDTO.getFirstPercent());

            //update second
            String[] value2 = currentASRatio.getSecond().split(",");
            if (requestDTO.getSecondCategory().equals(value2[0]))
                if (requestDTO.getSecondPercent() != Integer.parseInt(value2[1]))
                    currentASRatio.updateSecond(requestDTO.getSecondCategory() + "," + requestDTO.getSecondPercent());

            //update third
            String[] value3 = currentASRatio.getThird().split(",");
            if (requestDTO.getThirdCategory().equals(value3[0]))
                if (requestDTO.getThirdPercent() != Integer.parseInt(value3[1]))
                    currentASRatio.updateThird(requestDTO.getThirdCategory() + "," + requestDTO.getThirdPercent());

            //update fourth
            String[] value4 = currentASRatio.getFourth().split(",");
            if (requestDTO.getFourthCategory().equals(value4[0]))
                if (requestDTO.getFourthPercent() != Integer.parseInt(value4[1]))
                    currentASRatio.updateFourth(requestDTO.getFourthCategory() + "," + requestDTO.getFourthPercent());

            ASRatioRepository.save(currentASRatio);

            // saving에는 다음달 abRatio 정할 때 반영.
            // 변동지출 칸에서 줄어든(절약하는) 사용금액은 추가 절약 형식으로 사용;
            if(ratio_type == 1){
                Long currentVariableTotal = requestDTO.getCurrentVariableTotal();
                Long lastVariableTotal = currentVariableTotal;//안쓸거임
                List<VariableBudgetResponseDTO> changedVariableCategoryCostList = getVariableBudgetResponse(currentVariableTotal, current, user, lastVariableTotal);

                return ResponseEntity.ok(changedVariableCategoryCostList);//추가 절약 금액을 받아서 다시 돌려줘야 하는지, 아니면 프론트에서 이동시킬 수 있는지
            } else if (ratio_type == 2) {
                Long currentScheduleTotal = requestDTO.getCurrentScheduleTotal();
                Long lastScheduleTotal = currentScheduleTotal;//안쓸거임
                List<ScheduleBudgetResponseDTO> changedScheduleCategoryCostList = allocateScheduleBudgetCore(current, Double.valueOf(currentScheduleTotal), lastScheduleTotal, user);

                return ResponseEntity.ok(changedScheduleCategoryCostList);//추가 절약 금액을 받아서 다시 돌려줘야 하는지, 아니면 프론트에서 이동시킬 수 있는지
            }
        }

        if(ratio_type == 1) return ResponseEntity.ok(requestDTO.getVariableBudgetResponseDTOS());
        else if (ratio_type == 2) return ResponseEntity.ok(requestDTO.getScheduleBudgetResponseDTOS());
        else return ResponseEntity.status(500).build();
    }


    @Transactional
    public ResponseEntity<?> allocateScheduleBudget(String email) throws Exception{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + email));
        LocalDate current = LocalDate.now();

        //전체 예산 - 일정에 사용 + 일정에 사용 x 비율
        //예산 테이블에서 전체 예산 불러오기 -> 이번달 예산 없으면 로직 끝
        Long currentBudget = budgetRepository.findByDateAndUser(
                current.withDayOfMonth(1), current.withDayOfMonth(current.lengthOfMonth()), user.getUserId());
        if(currentBudget == null)
            return ResponseEntity.ok().build();

        //이전 달 정보: 이전 달 / 이전 달 예산 / 이전 달 카테고리별 금액 총합(카테고리명-금액 합)
        LocalDate lastMonth = current.minusMonths(1);
        Long lastBudget = budgetRepository.findByDateAndUser(
                lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()), user.getUserId());
        Long lastScheduleExpenseTotal = scheduleRepository.findTotalExpenseByUserAndDate(user, lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));

        Double scheduleRatioPerTotal = Double.valueOf(lastScheduleExpenseTotal / lastBudget);

        //일정 예산 할당에 사용할 이번달 일정 예산
        Double currentScheduleBudget = currentBudget * scheduleRatioPerTotal;
//        //일정 카테고리별 비율 설정용 - 전체 카테고리 가져와서 할당할 때
//        List<Object[]> lastCategoryExpenseList = scheduleRepository.findCategoryExpenseSumByUserAndDate(
//                user.getUserId(), lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));

        List<ScheduleBudgetResponseDTO> scheduleCategoryCostList = allocateScheduleBudgetCore(current, currentScheduleBudget, lastScheduleExpenseTotal, user);
        ScheduleAllocationResponseDTO scheduleAllocationResponseDTO = new ScheduleAllocationResponseDTO(currentScheduleBudget.longValue(), scheduleCategoryCostList);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleCategoryCostList);
    }

    @Transactional
    public List<ScheduleBudgetResponseDTO> allocateScheduleBudgetCore(LocalDate current, Double currentScheduleBudget, Long lastScheduleExpenseTotal, User user) throws Exception{
        LocalDate lastMonth = current.minusMonths(1);

        List<ScheduleBudgetResponseDTO> scheduleCategoryCostList = new ArrayList<>();
        List<String[]> result = ASRatioRepository.findASByUserAndDate(current.withDayOfMonth(1), current.withDayOfMonth(current.lengthOfMonth()), user.getUserId(), 2);
        if(result != null && result.get(0).length == 4){//first: FOOD,40 second: TRANSPORTATION,30
            String[] resultArray = result.get(0);
            for (int i = 0; i < 4; i++) {
                String[] value = resultArray[i].split(",");//0:FOOD 1:40(%)
                ScheduleBudgetResponseDTO scheduleBudget = new ScheduleBudgetResponseDTO(
                        ScheduleCategory.valueOf(value[0]), currentScheduleBudget*(Double.parseDouble(value[1])/100), Integer.parseInt(value[1]));
                scheduleCategoryCostList.add(scheduleBudget);
            }
        } else {
            //ASRatio에 이번 거(지난 달) 저장 ->findTop4CategoriesWithinMonth
            List<Object[]> top4CategoryCostList = scheduleRepository.findTop4CategoriesWithinMonth(
                    lastMonth.withDayOfMonth(1).atStartOfDay(), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()).atTime(LocalTime.MAX), user.getUserId());
            String[] fstf1234 = new String[4];

            for (int i = 0; i < top4CategoryCostList.size(); i++) {
                Object[] entry = top4CategoryCostList.get(i);
                fstf1234[i] = createASRatio(entry, lastScheduleExpenseTotal);//이전달 변동자산 total
            }
            ASRatioRepository.save(new ASRatio(user, fstf1234[0], fstf1234[1], fstf1234[2], fstf1234[3], 1));

            //이번달 일정 카테고리별 예산 할당
            for (int i = 0; i < 4; i++) {
                String[] value = fstf1234[i].split(",");//0:FOOD 1:40(%)
                ScheduleBudgetResponseDTO scheduleBudget2 = new ScheduleBudgetResponseDTO(ScheduleCategory.valueOf(value[0]), currentScheduleBudget * (Double.parseDouble(value[1]) / 100), Integer.parseInt(value[1]));
                scheduleCategoryCostList.add(scheduleBudget2);
            }
        }
        return scheduleCategoryCostList;
    }

}
