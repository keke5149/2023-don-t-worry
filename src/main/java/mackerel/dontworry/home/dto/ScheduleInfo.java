package mackerel.dontworry.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mackerel.dontworry.schedule.domain.ScheduleCategory;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleInfo {

    private Long scheduleId;
    private ScheduleCategory category;
    private String title;
    private Long income;
    private Long expense;
    private LocalDate scheduleDate;

}
