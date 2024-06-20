package mackerel.dontworry.budgetguide.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleAllocationResponseDTO {
    private Long currentScheduleTotal;
    List<ScheduleBudgetResponseDTO> scheduleBudgetResponseDTOS;
}
