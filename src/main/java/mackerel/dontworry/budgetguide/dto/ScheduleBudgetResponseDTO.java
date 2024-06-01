package mackerel.dontworry.budgetguide.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mackerel.dontworry.schedule.domain.ScheduleCategory;

@Getter
//@Setter
//@Builder
@AllArgsConstructor
public class ScheduleBudgetResponseDTO {
    private ScheduleCategory category;
    private Double costSum;
    private int percent;
}
