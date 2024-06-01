package mackerel.dontworry.budgetguide.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mackerel.dontworry.accountbook.domain.AccountCategory;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FixedBudgetResponseDTO {
    private AccountCategory category;
    private Long costSum;
}
