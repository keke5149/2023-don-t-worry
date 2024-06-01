package mackerel.dontworry.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainInfo {

    private List<ScheduleInfo> schedules;
    private List<InexInfo> inexs;
    private Double currentBudgetUsagePercent;
}
