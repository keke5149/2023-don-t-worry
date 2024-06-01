package mackerel.dontworry.budgetguide.repository;

import mackerel.dontworry.budgetguide.domain.ASRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ASRatioRepository extends JpaRepository<ASRatio, Long> {
    @Query(value = "SELECT first, second, third, fourth FROM ASRATIO " +
            "WHERE user_id = :user AND ratio_type = :type AND created_at BETWEEN :startDate AND :endDate " +
            "ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    List<String []> findASByUserAndDate(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate,
                                      @Param ("user") Long user,
                                      @Param("type") int ratio_type);

    @Query(value = "SELECT * FROM ASRATIO " +
            "WHERE user_id = :user AND ratio_type = :type AND created_at BETWEEN :startDate AND :endDate " +
            "ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    ASRatio findEntityByUserAndDate(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param ("user") Long user,
                                    @Param("type") int ratio_type);

}
