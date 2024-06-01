package mackerel.dontworry.budgetguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
//@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRatioUpdateRequestDTO {

    private String username;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String fourthCategory;
    private int firstPercent;
    private int secondPercent;
    private int thirdPercent;
    private int fourthPercent;

    List<VariableBudgetResponseDTO> variableBudgetResponseDTOS;
    List<ScheduleBudgetResponseDTO> scheduleBudgetResponseDTOS;
    private Long currentVariableTotal;
    private Long currentScheduleTotal;

    public void setUsername(String username) {
        this.username = username;
    }
}
