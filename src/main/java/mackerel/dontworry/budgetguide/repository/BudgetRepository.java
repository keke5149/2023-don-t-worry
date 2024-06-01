package mackerel.dontworry.budgetguide.repository;

import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.budgetguide.domain.Budget;
import mackerel.dontworry.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query(value = "SELECT budget FROM budget " +
            "WHERE user_id = :userId AND budget_date BETWEEN :startDate AND :endDate ", nativeQuery = true)
    Long findByDateAndUser(@Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate,
                                                            @Param ("userId") Long userId);
}
