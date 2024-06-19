package mackerel.dontworry.challenge.repository;

import mackerel.dontworry.challenge.domain.CategoryGoalTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryGoalTrackerRepository extends JpaRepository<CategoryGoalTracker, Long> {
    Long countByCategoryId(String categoryId);
}
