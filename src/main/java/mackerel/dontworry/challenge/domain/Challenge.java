package mackerel.dontworry.challenge.domain;

import jakarta.persistence.*;
import lombok.*;
import mackerel.dontworry.user.domain.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
//@Builder
@Table(name = "CHALLENGE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "goal_amount", nullable = false)
    private Long goalAmount;

    @ManyToMany
    @JoinTable(
            name = "challenge_users",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    public Challenge(String title, LocalDate startDate, LocalDate endDate, Long goalAmount) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalAmount = goalAmount;
    }


    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }
    public void addParticipant(User user) {
        participants.add(user);
        user.getChallenges().add(this);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
        user.getChallenges().remove(this);
    }
}
