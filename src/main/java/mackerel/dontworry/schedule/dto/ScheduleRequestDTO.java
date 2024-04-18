package mackerel.dontworry.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mackerel.dontworry.schedule.domain.Schedule;
import mackerel.dontworry.schedule.domain.ScheduleCategory;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDTO {

    private Long scheduleId;
    private String username;
    private ScheduleCategory category;
    private String title;
    private Long income;
    private Long expense;
    private String memo;
    private int year;
    private int month;
    private int day;

    public Schedule toEntity(){
        return Schedule.builder()
                .category(category)
                .title(title)
                .memo(memo)
                .scheduleDate(LocalDate.of(year, month, day))
                .build();

    }
}
