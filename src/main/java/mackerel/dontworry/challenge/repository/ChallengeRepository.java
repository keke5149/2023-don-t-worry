package mackerel.dontworry.challenge.repository;

import mackerel.dontworry.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
