package mackerel.dontworry.budgetguide.repository;

import mackerel.dontworry.budgetguide.domain.FixedEX;
import mackerel.dontworry.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FixedEXRepository extends JpaRepository<FixedEX, Long> {
    @Query(value = "SELECT fixed_list FROM FIXEDEX " +
            "WHERE user_id = :email ", nativeQuery = true)
    String findByUser(@Param("email") String email);
}
