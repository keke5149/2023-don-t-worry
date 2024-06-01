package mackerel.dontworry.schedule.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.global.exception.NotFoundContentException;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import mackerel.dontworry.schedule.domain.Schedule;
import mackerel.dontworry.schedule.dto.ScheduleRequestDTO;
import mackerel.dontworry.schedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 등록

     * @param requestDTO 일정 생성 요청
     * @return 생성된 일정
     */
    @Transactional
    public ResponseEntity<?> createSchedule(ScheduleRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));

        Schedule schedule = requestDTO.toEntity();
        schedule.setUser(user);
        schedule.setIncome(0L);
        schedule.setExpense(0L);
        scheduleRepository.save(schedule);
        return ResponseEntity.status(201).body(schedule);
    }

    /**
     * 일정 업데이트
     */

    @Transactional
    public ResponseEntity<?> updateSchedule(ScheduleRequestDTO requestDTO) throws IOException {

        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        Schedule schedule = scheduleRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundContentException("없는 일정입니다."));


        if(requestDTO.getCategory() != null)
            schedule.updateCategory(requestDTO.getCategory());
        if(requestDTO.getTitle() != null)
            schedule.updateTitle(requestDTO.getTitle());
        if(requestDTO.getIncome() != null)
            schedule.updateIncome(requestDTO.getIncome());
        if(requestDTO.getExpense() != null)
            schedule.updateExpense(requestDTO.getExpense());
        if(requestDTO.getMemo() != null)
            schedule.updateMemo(requestDTO.getMemo());

        int year = schedule.getScheduleDate().getYear();
        int month = schedule.getScheduleDate().getMonth().getValue();
        int day = schedule.getScheduleDate().getDayOfMonth();

        if(year != requestDTO.getYear()  || month != requestDTO.getMonth() || day != requestDTO.getDay()) {
            if (year != requestDTO.getYear())
                schedule.getScheduleDate().withYear(requestDTO.getYear());
            if (month != requestDTO.getMonth())
                schedule.getScheduleDate().withMonth(requestDTO.getMonth());
            if(day != requestDTO.getDay())
                schedule.getScheduleDate().withDayOfMonth(requestDTO.getDay());
        }

        return ResponseEntity.ok(schedule);
    }

    /**
     * 일정 삭제
     * 유저, 일정 리포지토리 확인 후 삭제
     */
    @Transactional
    public ResponseEntity<?> deleteSchedule(Long recordId, String username) throws IOException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + username));
        Schedule schedule = scheduleRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundContentException("없는 일정입니다."));

        if(schedule.getUser().equals(user)){
            scheduleRepository.delete(schedule);
            return ResponseEntity.ok("DELETE SUCCESS");
        } else{
            return ResponseEntity.ok("DELETE FAILED");
        }
    }
}
