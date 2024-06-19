package mackerel.dontworry.budgetguide.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mackerel.dontworry.user.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "BUDGET")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="budget_id")
    private Long budgetId;

    private LocalDate budgetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private Long budget;

    public Budget(LocalDate budgetDate, User user, Long budget) {
        this.budgetDate = budgetDate;
        this.user = user;
        this.budget = budget;
    }

}
