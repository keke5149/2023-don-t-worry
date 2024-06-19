package mackerel.dontworry.statistic.repository;

import mackerel.dontworry.statistic.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT FUNCTION('YEAR', a.date) as year, SUM(a.actualExpense) as actualExpense, SUM(a.divAccount) as divAccount, SUM(a.expAccount) as expAccount " +
            "FROM Statistic a GROUP BY FUNCTION('YEAR', a.date) ORDER BY FUNCTION('YEAR', a.date)")
    List<Object[]> findYearlyStatistics();
}
