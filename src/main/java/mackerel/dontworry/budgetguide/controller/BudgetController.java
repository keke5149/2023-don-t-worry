package mackerel.dontworry.budgetguide.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.budgetguide.dto.CategoryRatioUpdateRequestDTO;
import mackerel.dontworry.budgetguide.dto.BudgetDTO;
import mackerel.dontworry.budgetguide.dto.FixedExDTO;
import mackerel.dontworry.budgetguide.service.BudgetGuideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/budget")
public class BudgetController {
    private final BudgetGuideService budgetGuideService;

    @PostMapping("") // budget update 추가 -> 한달에 budget 한번만 넣을 수 있게
    public ResponseEntity<?> saveBudget(@RequestBody BudgetDTO budgetDTO) throws Exception{
        String email = getEmailFromAuthentication();
        budgetDTO.setUsername(email);
        return budgetGuideService.saveBudget(budgetDTO);
    }

    //한달 예산도 fixed 선택할 때 입력하는가? -> 매달 한달 예산을 입력할 때마다 fixed를 선택해야 하는지지
    @PatchMapping("/fixed-categories")
    public ResponseEntity<?> saveFixedCategory(@RequestBody FixedExDTO requestDTO) throws Exception{
        String email = getEmailFromAuthentication();
        if(email != null){
            requestDTO.setUsername(email);
            return budgetGuideService.saveFixedCategory(requestDTO);
        } else {
            // 사용자가 인증되지 않은 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/accountbook") // 유동지출-고정지출-절약
    public ResponseEntity<?> readAccountBookBudget() throws Exception{
        String email = getEmailFromAuthentication();
        return budgetGuideService.allocateAccountBookBudget(email);
    }

    @GetMapping("/schedules") // 유동지출-고정지출-절약
    public ResponseEntity<?> readScheduleBudget() throws Exception{
        String email = getEmailFromAuthentication();
        return budgetGuideService.allocateScheduleBudget(email);
    }

    @PatchMapping(value = {"/accountbook/category-ratio", "/schedules/category-ratio"})
    public ResponseEntity<?> updateCategoryRatio(@RequestBody CategoryRatioUpdateRequestDTO requestDTO) throws Exception{
        String email = getEmailFromAuthentication();
        if(email != null){
            requestDTO.setUsername(email);
            return budgetGuideService.updateCategoryRatio(requestDTO);
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }




    private String getEmailFromAuthentication() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return authentication.getPrincipal().getAttributes().get("email").toString();
        } else {
            // 인증되지 않은 경우 null
            return null;
        }
    }
}
