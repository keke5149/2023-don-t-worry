package mackerel.dontworry.statistic.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.statistic.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticsService statisticsService;

    @GetMapping
    public Map<String, Object> getYearlyStatistics() {
        return statisticsService.getYearlyStatistics();
    }
}