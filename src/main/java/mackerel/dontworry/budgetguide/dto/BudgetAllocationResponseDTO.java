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
public class BudgetAllocationResponseDTO {
    private Long currentBudget;
    private Long currentSaving;
    private Long currentFixedTotal;
    private Long currentVariableTotal;
    List<FixedBudgetResponseDTO> fixedBudgetResponseDTO;
    List<VariableBudgetResponseDTO> variableBudgetResponseDTO;
}
