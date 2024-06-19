package mackerel.dontworry.challenge.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
//@Builder
@Table(name = "DAILYSPENDING")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailySpending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private LocalDate date;

    public double getAmount() {
        return 0;
    }

    // Getters and setters
    // ...
}
