package mackerel.dontworry.challenge.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.challenge.domain.MoneyCollector;
import mackerel.dontworry.challenge.dto.*;
import mackerel.dontworry.challenge.service.ChallengeService;
import mackerel.dontworry.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final UserService userService;
    private final ChallengeService challengeService;

    @GetMapping("")
    public ResponseEntity<?> getChallenges(){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/friends")
    public ResponseEntity<Void> addFriend(@RequestBody FriendDTO friendDTO) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getPrincipal().getAttributes().get("email").toString();
            friendDTO.setUsername(username);
            userService.addFriend(friendDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // 인증되지 않은 경우 null
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/with-friends")
    public ResponseEntity<?> createGroupChallenge(@RequestBody GroupChallengeDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(challengeService.createGroupChallenge(requestDTO));
    }

    @PostMapping("/category-goal")
    public ResponseEntity<String> checkCategoryGoal(@RequestBody CategoryGoalTrackerDTO categoryGoalCategoryGoalTrackerDto) {
        String result = challengeService.checkCategoryGoal(categoryGoalCategoryGoalTrackerDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/daily-spending")
    public ResponseEntity<String> checkDailySpending(@RequestBody DailySpendingDTO dailySpendingDTO) {
        String result = challengeService.checkDailySpending(dailySpendingDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<MoneyCollector> createChallenge(@RequestBody MoneyCollectorDTO challengeDTO) {
        MoneyCollector challenge = challengeService.createMoneyCollectorChallenge(challengeDTO);
        return new ResponseEntity<>(challenge, HttpStatus.CREATED);
    }
}
