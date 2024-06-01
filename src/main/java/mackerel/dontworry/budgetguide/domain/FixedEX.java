package mackerel.dontworry.budgetguide.domain;


import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.user.domain.User;

@Entity
@Getter
@Table(name = "FIXEDEX")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedEX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="budget_id")
    private Long budgetId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String fixedList;

    @Builder
    public FixedEX(User user, String fixedList) {
        this.user = user;
        this.fixedList = fixedList;
    }
}
