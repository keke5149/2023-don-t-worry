package mackerel.dontworry.challenge.dto;

import lombok.*;
import mackerel.dontworry.challenge.domain.Challenge;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupChallengeDTO {
    private List<String> friendEmails;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long goalAmount;

}
