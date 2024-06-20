package mackerel.dontworry.home.dto;

import lombok.*;
import mackerel.dontworry.schedule.domain.ScheduleCategory;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleInfo {

    private Long scheduleId;
    private ScheduleCategory category;
    private String title;
    private Long income;
    private Long expense;
    private LocalDate scheduleDate;

    private Long expectedCost;
    private Double recommendedCost;

    public ScheduleInfo(Long scheduleId, ScheduleCategory category, String title, Long income, Long expense, LocalDate scheduleDate) {
        this.scheduleId = scheduleId;
        this.category = category;
        this.title = title;
        this.income = income;
        this.expense = expense;
        this.scheduleDate = scheduleDate;
    }
}
