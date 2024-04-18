package mackerel.dontworry.challenge.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "CHALLENGE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="challenge_id")
    private Long challengeId;
}
