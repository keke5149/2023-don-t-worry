package mackerel.dontworry.home.dto;

import lombok.*;
import mackerel.dontworry.accountbook.domain.AccountCategory;
import mackerel.dontworry.accountbook.domain.InEx;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class InexInfo {

    private Long recordId;
    private AccountCategory category;
    private InEx inex;
    private String title;
    private Long cost;
    private LocalDateTime createdAt;
}
