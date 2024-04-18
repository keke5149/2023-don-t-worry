package mackerel.dontworry.accountbook.repository;

import mackerel.dontworry.accountbook.domain.AccountBook;
import mackerel.dontworry.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    Optional<AccountBook> findByAccountIdAndUser(Long accountId, User user);
    List<AccountBook> findAllByUser(User user);
}
