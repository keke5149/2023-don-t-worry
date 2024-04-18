package mackerel.dontworry.schedule.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.schedule.dto.ScheduleRequestDTO;
import mackerel.dontworry.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequestDTO requestDTO) throws Exception{
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            requestDTO.setUsername(email);
            return scheduleService.createSchedule(requestDTO);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<?> updateSchedule(@PathVariable(name = "recordId")Long recordId, @RequestBody ScheduleRequestDTO requestDTO) throws Exception{
        requestDTO.setScheduleId(recordId);
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            requestDTO.setUsername(email);
            return scheduleService.updateSchedule(requestDTO);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> removeSchedule(@PathVariable(name = "recordId")Long recordId) throws Exception{
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().getAttributes().get("email").toString();
            return scheduleService.deleteSchedule(recordId, email);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
