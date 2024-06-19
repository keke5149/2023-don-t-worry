package mackerel.dontworry.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mackerel.dontworry.accountbook.domain.AccountCategory;
import mackerel.dontworry.accountbook.domain.InEx;

@Getter
@Setter
@AllArgsConstructor
public class BudgetUsageDTO {
    int percent;
    Long usage;
    Long currentBudget;
}
