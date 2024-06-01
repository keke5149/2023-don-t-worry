package mackerel.dontworry.accountbook.repository;

import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    Optional<AccountBook> findByAccountIdAndUser(Long accountId, User user);
    List<AccountBook> findAllByUser(User user);

    //수정 -> inex 구분... ㅁㅊ;

    //가장 빈도가 높은 카테고리의 이름, 빈도수, 가격 총합
    @Query(value = "SELECT category, SUM(cost) AS total_cost FROM account " +
            "WHERE user_id = :user AND created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY category ORDER BY total_cost DESC LIMIT 4", nativeQuery = true)
    List<Object[]> findTop4CategoriesWithinMonth(@Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate, @Param("user") Long userId);

    @Query(value = "SELECT category, SUM(cost) FROM account " +
            "WHERE user_id = :user AND created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY category", nativeQuery = true)
    List<Object[]> findCategoryCostSumByUser(@Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate,
                                                     @Param("user") Long user_id);

    @Query("SELECT SUM(a.cost) FROM AccountBook a " +
            "WHERE a.user = :user AND a.createdAt BETWEEN :startDate AND :endDate")
    Long findTotalCostByUserAndDateRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT u.email, SUM(a.cost) FROM AccountBook a " +
            "JOIN a.user u " +
            "WHERE u IN :friends AND a.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY u.email")
    List<Object[]> findFriendBudgetUsage(@Param("friends") List<User> friends,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);


}
