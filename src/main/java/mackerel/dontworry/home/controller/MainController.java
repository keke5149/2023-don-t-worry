package mackerel.dontworry.home.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.service.AccountBookService;
import mackerel.dontworry.home.service.MainService;
import mackerel.dontworry.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    //추천 금액도 같이 보..내야 함. .. 추천금액+예측금액 같이...뻥이겠지
    @GetMapping(value = "/api/v1/main")
    public ResponseEntity<?> home(){
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            return ResponseEntity.ok(mainService.getMainInfo(email));
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }


}
