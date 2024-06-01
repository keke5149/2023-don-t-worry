package mackerel.dontworry.schedule.repository;


import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser(User user);
    List<Schedule> findAllByUser(User user);

    @Query("SELECT SUM(a.expense) FROM Schedule a " +
            "WHERE a.user = :user AND a.scheduleDate BETWEEN :startDate AND :endDate")
    Long findTotalExpenseByUserAndDate(@Param("user") User user,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT category, SUM(expense) FROM SCHEDULE " +
            "WHERE user_id = :user AND schedule_date BETWEEN :startDate AND :endDate " +
            "GROUP BY category", nativeQuery = true)
    List<Object[]> findCategoryExpenseSumByUserAndDate(@Param("user") Long user_id,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT category, SUM(cost) AS total_cost FROM SCHEDULE " +
            "WHERE user_id = :user AND schedule_date BETWEEN :startDate AND :endDate " +
            "GROUP BY category ORDER BY total_cost DESC LIMIT 4", nativeQuery = true)
    List<Object[]> findTop4CategoriesWithinMonth(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate, @Param("user") Long userId);

}
