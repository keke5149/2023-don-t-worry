package mackerel.dontworry.accountbook.repository;

import mackerel.dontworry.accountbook.domain.Repeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepeatRepository extends JpaRepository<Repeat, Long> {
    @Query("SELECT r FROM Repeat r WHERE DATE(r.repeatDate) = CURRENT_DATE")
    List<Repeat> findByRepeatDateToday();
}
