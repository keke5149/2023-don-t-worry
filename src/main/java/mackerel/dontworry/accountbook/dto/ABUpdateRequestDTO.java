package mackerel.dontworry.accountbook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mackerel.dontworry.accountbook.domain.AccountCategory;
import mackerel.dontworry.accountbook.domain.InEx;
import mackerel.dontworry.accountbook.domain.MoneyType;

@Getter
@Setter
@NoArgsConstructor
public class ABUpdateRequestDTO {
    private Long recordId;
    private String username;
    private Long scheduleId;
    private MoneyType moneyType;
    private AccountCategory category;
    private InEx inex;
    private boolean repeatFlag;
    private int cycleN;
    private String cycleP;
    private String title;
    private Long cost;
    private String memo;
}
