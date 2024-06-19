package mackerel.dontworry.challenge.repository;

import mackerel.dontworry.challenge.domain.MoneyCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyCollectorRepository extends JpaRepository<MoneyCollector, Long> {
}
