package mackerel.dontworry.challenge.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@Builder
@Table(name = "MONEYCOLLECTOR")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyCollector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int period;
    private double totalMoney;
    private double monthlySavings;

    public MoneyCollector(int period, double totalMoney) {
        this.period = period;
        this.totalMoney = totalMoney;
        this.monthlySavings = calculateMonthlySavings();
    }

    public double calculateMonthlySavings() {
        return totalMoney / period;
    }

    // Getters and setters
    // ...
}
