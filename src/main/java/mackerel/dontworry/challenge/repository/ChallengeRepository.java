package mackerel.dontworry.challenge.repository;

import mackerel.dontworry.challenge.domain.WithFriends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<WithFriends, Long> {
}
