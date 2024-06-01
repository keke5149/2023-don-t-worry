package mackerel.dontworry.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.budgetguide.repository.BudgetRepository;
import mackerel.dontworry.challenge.dto.FriendDTO;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(FriendDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));

        User friend = userRepository.findByEmail(requestDTO.getFriend())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));

        user.getFriends().add(friend);//메모리 변화
        friend.getFriends().add(user);

        userRepository.save(user);//데이터베이스에 반영
        userRepository.save(friend);
    }
}

