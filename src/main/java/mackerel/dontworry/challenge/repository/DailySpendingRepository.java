package mackerel.dontworry.challenge.repository;

import mackerel.dontworry.challenge.domain.DailySpending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailySpendingRepository extends JpaRepository<DailySpending, Long> {
    List<DailySpending> findByDate(LocalDate date);
}