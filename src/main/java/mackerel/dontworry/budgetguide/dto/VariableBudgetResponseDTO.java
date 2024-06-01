package mackerel.dontworry.budgetguide.dto;

import lombok.*;
import mackerel.dontworry.accountbook.domain.AccountCategory;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VariableBudgetResponseDTO {
    private AccountCategory category;
    private Double costSum;
    private int percent;
}
