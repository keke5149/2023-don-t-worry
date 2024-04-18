package mackerel.dontworry.home.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final AccountBookRepository accountBookRepository;


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
        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<AccountBook> accountBooks = accountBookRepository.findAllByUser(user);

        if (!schedules.isEmpty()) {
            for (Schedule sche : schedules) {
                ScheduleInfo info = new ScheduleInfo(sche.getScheduleId(), sche.getCategory(), sche.getTitle(), sche.getIncome(), sche.getExpense(), sche.getScheduleDate());
                scheduleInfos.add(info);
            }
        }
        if (!inexInfos.isEmpty()) {
            for (AccountBook account : accountBooks) {
                InexInfo info = new InexInfo(account.getAccountId(), account.getCategory(), account.getInex(), account.getTitle(), account.getCost());
                inexInfos.add(info);
            }
        }
        result.setSchedules(scheduleInfos);
        result.setInexs(inexInfos);
        return result;
    }
}