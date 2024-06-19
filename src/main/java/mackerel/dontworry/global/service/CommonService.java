package mackerel.dontworry.global.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.budgetguide.repository.BudgetRepository;
import mackerel.dontworry.global.dto.BudgetUsageDTO;
import mackerel.dontworry.user.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final AccountBookRepository accountBookRepository;
    private final BudgetRepository budgetRepository;

    //1인 예산 사용량
    @Transactional
    public BudgetUsageDTO readCurrentBudgetUsage(User user){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = currentDateTime.toLocalDate();
        Long getCurrentUsage = accountBookRepository.findTotalCostByUserAndDateRange(user, currentDateTime.withDayOfMonth(1), currentDateTime);
        Long currentTotalBudget = budgetRepository.findByDateAndUser(currentDate.withDayOfMonth(1), currentDate.withDayOfMonth(currentDate.lengthOfMonth()), user.getUserId());

        int percent;
        Long currentUsage;
        if(getCurrentUsage != null){
            currentUsage = getCurrentUsage;
            percent = (int) (currentUsage.doubleValue() / currentTotalBudget.doubleValue() * 100);
        } else{
            currentUsage = 0L;
            percent = 100;
        }

        return new BudgetUsageDTO(percent, currentUsage, currentTotalBudget);
    }
}
