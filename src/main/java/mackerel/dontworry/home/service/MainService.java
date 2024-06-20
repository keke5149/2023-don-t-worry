package mackerel.dontworry.home.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.budgetguide.dto.ScheduleAllocationResponseDTO;
import mackerel.dontworry.budgetguide.dto.ScheduleBudgetResponseDTO;
import mackerel.dontworry.budgetguide.service.BudgetGuideService;
import mackerel.dontworry.global.dto.BudgetUsageDTO;
import mackerel.dontworry.global.service.CommonService;
import mackerel.dontworry.home.dto.InexInfo;
import mackerel.dontworry.home.dto.MainInfo;
import mackerel.dontworry.home.dto.ScheduleInfo;
import mackerel.dontworry.schedule.domain.Schedule;
import mackerel.dontworry.schedule.repository.ScheduleRepository;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final AccountBookRepository accountBookRepository;

    private final CommonService commonService;
    private final BudgetGuideService budgetGuideService;
    /**
     * 메인 화면
     * 유저 일정, 가계부 조회
     *
     * @param username 유저 이메일
     * @return 유저 일정, 가계부 정보 리스트
     */
    @Transactional(readOnly = true)
    public MainInfo getMainInfo(String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + username));

        MainInfo result = new MainInfo();
        List<ScheduleInfo> scheduleInfos = new ArrayList<>();
        List<InexInfo> inexInfos = new ArrayList<>();

        LocalDateTime currentDateTime = LocalDateTime.now();
        BudgetUsageDTO currentUsage = commonService.readCurrentBudgetUsage(user);

        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<AccountBook> accountBooks = accountBookRepository.findAllByUser(user);
        List<ScheduleBudgetResponseDTO> scheduleBudget;
        try {
            ScheduleAllocationResponseDTO scheduleBudgetAllocation = budgetGuideService.allocateScheduleBudget(username);
            scheduleBudget = scheduleBudgetAllocation.getScheduleBudgetResponseDTOS();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!schedules.isEmpty()) {
            for (Schedule sche : schedules) {
                ScheduleInfo info = new ScheduleInfo(sche.getScheduleId(), sche.getCategory(), sche.getTitle(), sche.getIncome(), sche.getExpense(), sche.getScheduleDate());
                if(scheduleBudget != null){
                    for(ScheduleBudgetResponseDTO recommended : scheduleBudget){
                        if(recommended.getCategory().equals(sche.getCategory())){
                            info.setRecommendedCost(recommended.getCostSum());
                            break;
                        }
                    }
                }
                scheduleInfos.add(info);
            }
        }
        if (!accountBooks.isEmpty()) {
            for (AccountBook account : accountBooks) {
                System.out.println(account.getAccountId());
                InexInfo info = new InexInfo(account.getAccountId(), account.getCategory(), account.getInex(), account.getTitle(), account.getCost(), account.getCreatedAt());
                inexInfos.add(info);
            }
        }

        result.setBudgetUsageDTO(currentUsage);
        result.setSchedules(scheduleInfos);
        result.setInexs(inexInfos);
        return result;
    }
}