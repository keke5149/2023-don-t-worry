package mackerel.dontworry.accountbook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mackerel.dontworry.accountbook.domain.*;

@Getter
@Setter
@NoArgsConstructor
public class ABRequestDTO {
    private String username;
    private Long schedule;//schedule id
    private MoneyType moneyType;
    private AccountCategory category;
    private InEx inex;
    private boolean repeatFlag;
    private int cycleN;
    private String cycleP;
    private String title;
    private Long cost;
    private String memo;

    public AccountBook toEntity() {
        return AccountBook.builder()
                .moneyType(moneyType)
                .category(category)
                .inex(inex)
                .title(title)
                .cost(cost)
                .memo(memo)
                .build();
    }

    public Repeat toEntityRepeat() {
        return Repeat.builder()
                .cycleNum(cycleN)
                .cyclePeriod(cycleP)
                .build();
    }
}
