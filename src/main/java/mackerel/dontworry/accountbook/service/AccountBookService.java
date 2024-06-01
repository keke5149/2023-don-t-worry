package mackerel.dontworry.accountbook.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.accountbook.domain.Repeat;
import mackerel.dontworry.accountbook.dto.ABRequestDTO;
import mackerel.dontworry.accountbook.dto.ABUpdateRequestDTO;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.accountbook.repository.RepeatRepository;
import mackerel.dontworry.global.exception.NotFoundContentException;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import mackerel.dontworry.schedule.domain.Schedule;
import mackerel.dontworry.schedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final RepeatRepository repeatRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 단독 가계부 레코드 등록

     * @param requestDTO 가계부 레코드 생성 요청
     * @return 생성된 가계부 레코드 내역
     */
    @Transactional
    public ResponseEntity<?> createAccountRecord(ABRequestDTO requestDTO) {
        AccountBook accountBook = requestDTO.toEntity();
        System.out.println(accountBook);
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        accountBook.setUser(user);

        if(requestDTO.getSchedule() != null){
            Schedule schedule = scheduleRepository.findById(requestDTO.getSchedule())
                    .orElseThrow(() -> new NotFoundContentException("일정을 찾을 수 없습니다: " + requestDTO.getSchedule()));
            accountBook.setSchedule(schedule);
        }
        //account_id를 위한 저장
        accountBook = accountBookRepository.save(accountBook);


        if(requestDTO.isRepeatFlag()){
            LocalDate current = LocalDate.now();
            switch (requestDTO.getCycleP()){
                case "DAY":
                    current = current.plusDays(requestDTO.getCycleN());
                    break;
                case "WEEK":
                    current = current.plusWeeks(requestDTO.getCycleN());
                    break;
                case "MONTH":
                    current = current.plusMonths(requestDTO.getCycleN());
                    break;
                case "YEAR":
                    current = current.plusYears(requestDTO.getCycleN());
                    break;
            }
            Repeat repeat = requestDTO.toEntityRepeat();
            repeat.setAccountBook(accountBook);
            repeat.setRepeatDate(current);
            repeatRepository.save(repeat);
            accountBook.setRepeat(repeat);
        }else {
            // 반복 여부를 선택하지 않은 경우 Repeat 엔티티를 생성하지 않고 null로 설정
            accountBook.setRepeat(null);
        }
        return ResponseEntity.status(201).body(accountBook);
    }

    /**
     * 반복 가계부 레코드 등록
     * @Scheduled 매일 00시
     * 새로운 AccountBook 데이터 등록
     * 다음 반복 날짜 재설정 -> 매일 돌린다는 건 너무... 흠 다른 방법 찾기
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void checkRepeat() {
        List<Repeat> repeatList = repeatRepository.findByRepeatDateToday();
        if(!repeatList.isEmpty()){
            for (Repeat repeat : repeatList) {
                // 새 레코드 생성
                AccountBook repeatRecord = repeat.getAccountBook();
                AccountBook newRecord = new AccountBook(repeatRecord.getUser(), repeatRecord.getSchedule(), repeatRecord.getMoneyType(), repeatRecord.getCategory(),
                        repeatRecord.getInex(), repeatRecord.getRepeat(), repeatRecord.getTitle(), repeatRecord.getCost(), repeatRecord.getMemo());
                accountBookRepository.save(repeatRecord);

                // 반복 날짜 재설정
                repeat.updateRepeatDate();
            }
        }
    }

    /**
     * 가계부 레코드 업데이트
     */

    @Transactional
    public ResponseEntity<?> updateAccountRecord(ABUpdateRequestDTO updateRequestDTO) throws IOException {
        // fot updating repeat cycle
        int num;
        String period;

        User user = userRepository.findByEmail(updateRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + updateRequestDTO.getUsername()));
        AccountBook accountRecord = accountBookRepository.findByAccountIdAndUser(updateRequestDTO.getRecordId(), user)
                .orElseThrow(() -> new NotFoundContentException("가계부 내역을 찾을 수 없습니다."));

        if(updateRequestDTO.getScheduleId() != null) {
            Schedule schedule = scheduleRepository.findById(updateRequestDTO.getScheduleId())
                    .orElseThrow(() -> new NotFoundContentException("없는 일정입니다."));
            accountRecord.setSchedule(schedule);
        }
        if(updateRequestDTO.getMoneyType() != null)
            accountRecord.updateMoneyType(updateRequestDTO.getMoneyType());
        if(updateRequestDTO.getCategory() != null)
            accountRecord.updateCategory(updateRequestDTO.getCategory());
        if(updateRequestDTO.getInex() != null)
            accountRecord.updateInex(updateRequestDTO.getInex());
        if(updateRequestDTO.getTitle() != null)
            accountRecord.updateTitle(updateRequestDTO.getTitle());
        if(updateRequestDTO.getCost() != null)
            accountRecord.updateCost(updateRequestDTO.getCost());
        if(updateRequestDTO.getMemo() != null)
            accountRecord.updateMemo(updateRequestDTO.getMemo());
        if(updateRequestDTO.isRepeatFlag() == true) {
            num = accountRecord.getRepeat().getCycleNum();
            period = accountRecord.getRepeat().getCyclePeriod();
            if (updateRequestDTO.getCycleN() != num)
                num = updateRequestDTO.getCycleN();
            if (updateRequestDTO.getCycleP() != null)
                period = updateRequestDTO.getCycleP();
            accountRecord.getRepeat().updateCycle(num, period);
        }

        return ResponseEntity.ok(updateRequestDTO);
    }

    /**
     * 가계부 레코드 삭제
     * 유저, 가계부 확인 후 삭제
     */
    @Transactional
    public ResponseEntity<?> deleteAccountRecord(Long recordId, String username) throws IOException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + username));
        AccountBook accountRecord = accountBookRepository.findByAccountIdAndUser(recordId, user)
                .orElseThrow(() -> new NotFoundContentException("가계부 내역을 찾을 수 없습니다."));

        if(accountRecord.getUser().equals(user)){
            accountBookRepository.delete(accountRecord);
            return ResponseEntity.ok("DELETE SUCCESS");
        } else{
            return ResponseEntity.ok("DELETE FAILED");
        }
    }

}
