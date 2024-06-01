package mackerel.dontworry.budgetguide.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.user.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "ASRATIO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ASRatio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ratio_id")
    private Long ratioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private LocalDate createdAt;
    private String first;
    private String second;
    private String third;
    private String fourth;

    // 1 -- ACCOUNT_BOOK
    // 2 -- SCHEDULE
    private int ratio_type;

    public ASRatio(User user, String first, String second, String third, String fourth, int ratio_type) {
        this.user = user;
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.ratio_type = ratio_type;
    }


    public void updateFirst(String first) {
        this.first = first;
    }

    public void updateSecond(String second) {
        this.second = second;
    }

    public void updateThird(String third) {
        this.third = third;
    }

    public void updateFourth(String fourth) {
        this.fourth = fourth;
    }
}
