package mackerel.dontworry.accountbook.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.dto.ABRequestDTO;
import mackerel.dontworry.accountbook.dto.ABUpdateRequestDTO;
import mackerel.dontworry.accountbook.service.AccountBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountbook")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping("")
    public ResponseEntity<?> createAccountRecord(@RequestBody ABRequestDTO requestDTO) throws Exception{
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            requestDTO.setUsername(email);
            return accountBookService.createAccountRecord(requestDTO);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<?> updateAccountRecord(@PathVariable(name = "recordId")Long recordId, @RequestBody ABUpdateRequestDTO requestDTO) throws Exception{
        requestDTO.setRecordId(recordId);
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            requestDTO.setUsername(email);
            return accountBookService.updateAccountRecord(requestDTO);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> removeAccountRecord(@PathVariable(name = "recordId")Long recordId) throws Exception{
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            return accountBookService.deleteAccountRecord(recordId, email);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
