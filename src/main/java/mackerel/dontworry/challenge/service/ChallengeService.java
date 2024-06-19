package mackerel.dontworry.challenge.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.challenge.domain.WithFriends;
import mackerel.dontworry.challenge.domain.DailySpending;
import mackerel.dontworry.challenge.domain.MoneyCollector;
import mackerel.dontworry.challenge.dto.CategoryGoalTrackerDTO;
import mackerel.dontworry.challenge.dto.DailySpendingDTO;
import mackerel.dontworry.challenge.dto.GroupChallengeDTO;
import mackerel.dontworry.challenge.dto.MoneyCollectorDTO;
import mackerel.dontworry.challenge.repository.CategoryGoalTrackerRepository;
import mackerel.dontworry.challenge.repository.ChallengeRepository;
import mackerel.dontworry.challenge.repository.DailySpendingRepository;
import mackerel.dontworry.challenge.repository.MoneyCollectorRepository;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final UserRepository userRepository;
    private final AccountBookRepository accountBookRepository;
    private final ChallengeRepository challengeRepository;
    private final MoneyCollectorRepository moneyCollectorRepository;
    private final DailySpendingRepository dailySpendingRepository;
    private final CategoryGoalTrackerRepository categoryGoalTrackerRepository;

    //친구들 예산 사용량
    @Transactional
    public Map<String, Long> readFriendBudget(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + username));
        Set<User> friends = user.getFriends();
        List<User> friendsList = friends.stream().collect(Collectors.toList());

        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Object[]> results = accountBookRepository.findFriendBudgetUsage(friendsList, currentDateTime.withDayOfMonth(1), currentDateTime);
        //현재까지의 예산 사용량만 있음 -> 친구의 이전달 예산도 가져와서 퍼센트로 만들어 줘야 함...

        Map<String, Long> friendBudgetUsageMap = new HashMap<>();
        for (Object[] result : results) {
            String friendEmail = (String) result[0];
            Long budgetUsage = (Long) result[1];
            friendBudgetUsageMap.put(friendEmail, budgetUsage);
        }
        return friendBudgetUsageMap;
    }

    @Transactional
    public WithFriends createGroupChallenge(GroupChallengeDTO requestDTO){

        WithFriends withFriends = new WithFriends(requestDTO.getTitle(), requestDTO.getStartDate(), requestDTO.getEndDate(), requestDTO.getGoalAmount());
        List<User> participants = userRepository.findByEmailIn(requestDTO.getFriendEmails());
        Set<User> participantsSet = new HashSet<>(participants);

        withFriends.setParticipants(participantsSet); // 현재 사용자를 포함한 친구들을 참여자로 설정
        challengeRepository.save(withFriends);
        return withFriends;
    }

    @Transactional
    public MoneyCollector createMoneyCollectorChallenge(MoneyCollectorDTO challengeDTO) {
        MoneyCollector challenge = new MoneyCollector(challengeDTO.getPeriod(), challengeDTO.getTotalMoney());
        return moneyCollectorRepository.save(challenge);
    }

    @Transactional
    public String checkDailySpending(DailySpendingDTO dailySpendingDTO) {
        List<DailySpending> accounts = dailySpendingRepository.findByDate(dailySpendingDTO.getDate());
        double totalSpent = accounts.stream().mapToDouble(DailySpending::getAmount).sum();
        return totalSpent <= dailySpendingDTO.getTargetAmount() ? "Success" : "Failure";
    }

    @Transactional
    public String checkCategoryGoal(CategoryGoalTrackerDTO categoryGoalCategoryGoalTrackerDto) {
        long usageCount = categoryGoalTrackerRepository.countByCategoryId(categoryGoalCategoryGoalTrackerDto.getCategory());
        return usageCount <= categoryGoalCategoryGoalTrackerDto.getTimes() ? "Success" : "Failure";
    }
}
