package mackerel.dontworry.accountbook.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.schedule.domain.Schedule;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private MoneyType moneyType;

    @Enumerated(EnumType.STRING)
    private AccountCategory category;
    @Enumerated(EnumType.STRING)
    private InEx inex;

    @OneToOne @JoinColumn(name = "repeat_id")
    private Repeat repeat;//FK 주인

    private String title;
    private Long cost;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public AccountBook(User user, Schedule schedule, MoneyType moneyType, AccountCategory category, InEx inex, Repeat repeat,
                       String title, Long cost, String memo){
        this.user = user;
        this.schedule = schedule;
        this.moneyType = moneyType;
        this.category = category;
        this.inex = inex;
        this.repeat = repeat;
        this.title = title;
        this.cost = cost;
        this.memo = memo;
        this.createdAt = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setSchedule(Schedule schedule){
        this.schedule = schedule;
    }
    public void setRepeat(Repeat repeat){
        this.repeat = repeat;
    }

    public void updateMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    public void updateCategory(AccountCategory category) {
        this.category = category;
    }

    public void updateInex(InEx inex) {
        this.inex = inex;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateCost(Long cost) {
        this.cost = cost;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = LocalDateTime.now();
    }
}
