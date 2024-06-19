package mackerel.dontworry.budgetguide.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BudgetDTO {
    private String username;
    private Long budget;
}
