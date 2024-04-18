package mackerel.dontworry.schedule.repository;


import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser(User user);
    List<Schedule> findAllByUser(User user);
}
