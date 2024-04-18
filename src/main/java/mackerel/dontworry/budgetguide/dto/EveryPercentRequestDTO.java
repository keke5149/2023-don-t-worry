package mackerel.dontworry.budgetguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EveryPercentRequestDTO {
    private String username;
    private int saveamount;
}
