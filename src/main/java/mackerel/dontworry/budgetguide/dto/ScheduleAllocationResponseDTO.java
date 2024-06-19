package mackerel.dontworry.budgetguide.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ScheduleAllocationResponseDTO {
    private Long currentScheduleTotal;
    List<ScheduleBudgetResponseDTO> scheduleBudgetResponseDTOS;
}
