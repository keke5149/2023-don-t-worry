package mackerel.dontworry.challenge.dto;

public class MoneyCollectorDTO {
    private int period;
    private double totalMoney;

    public MoneyCollectorDTO() {
    }

    public MoneyCollectorDTO(int period, double totalMoney) {
        this.period = period;
        this.totalMoney = totalMoney;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
