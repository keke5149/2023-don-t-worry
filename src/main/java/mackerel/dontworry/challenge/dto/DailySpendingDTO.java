package mackerel.dontworry.challenge.dto;

import java.time.LocalDate;

public class DailySpendingDTO {
    private LocalDate date;
    private double targetAmount;

    public DailySpendingDTO() {
    }

    public DailySpendingDTO(LocalDate date, double targetAmount) {
        this.date = date;
        this.targetAmount = targetAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }
}
