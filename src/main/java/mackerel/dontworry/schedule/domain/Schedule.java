package mackerel.dontworry.schedule.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.user.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ScheduleCategory category;
    private String title;
    private Long income;
    private Long expense;
    private String memo;
    private LocalDate scheduleDate;

    @Builder
    public Schedule(User user, ScheduleCategory category, String title, String memo, LocalDate scheduleDate){
        this.user = user;
        this.category = category;
        this.title = title;
        this.income = 0L;
        this.expense = 0L;
        this.memo = memo;
        this.scheduleDate = scheduleDate;
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setIncome(Long income){
        this.income = income;
    }
    public void setExpense(Long expense){
        this.expense = expense;
    }

    public void updateCategory(ScheduleCategory category) {
        this.category = category;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateIncome(Long income){
        this.income = income;
    }
    public void updateExpense(Long expense){
        this.expense = expense;
    }
    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
