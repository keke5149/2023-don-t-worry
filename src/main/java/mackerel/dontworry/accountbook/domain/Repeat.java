package mackerel.dontworry.accountbook.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "ACCOUNT")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Repeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="repeat_id")
    private Long repeatId;

    @OneToOne(mappedBy = "repeat")
    private AccountBook accountBook;

    private LocalDate repeatDate;
    private int cycleNum;
    private String cyclePeriod;

    @Builder
    public Repeat(AccountBook accountBook, LocalDate localDate, int cycleNum, String cyclePeriod){
        this.accountBook = accountBook;
        this.repeatDate = localDate;
        this.cycleNum = cycleNum;
        this.cyclePeriod = cyclePeriod;
    }

    public void setAccountBook(AccountBook accountBook){
        this.accountBook = accountBook;
    }
    public void setRepeatDate(LocalDate next){
        this.repeatDate = next;
    }

    public void updateCycle(int cycleNum, String cyclePeriod){
        this.cycleNum = cycleNum;
        this.cyclePeriod = cyclePeriod;
    }

    public void updateRepeatDate(){
        switch (this.cyclePeriod){
            case "DAY":
                this.repeatDate = this.repeatDate.plusDays(this.getCycleNum());
                break;
            case "WEEK":
                this.repeatDate = this.repeatDate.plusWeeks(this.getCycleNum());
                break;
            case "MONTH":
                this.repeatDate = this.repeatDate.plusMonths(this.getCycleNum());
                break;
            case "YEAR":
                this.repeatDate = this.repeatDate.plusYears(this.getCycleNum());
                break;
        }
    }
}
