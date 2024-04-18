package mackerel.dontworry.budgetguide.controller;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.budgetguide.dto.EveryPercentRequestDTO;
import mackerel.dontworry.budgetguide.service.BudgetGuideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetGuideService budgetGuideService;

    @PostMapping("/percent")
    public ResponseEntity<?> savePercent(EveryPercentRequestDTO requestDTO) throws Exception{
        return budgetGuideService.savePercent(requestDTO);
    }

    @PostMapping("/everyday")
    public ResponseEntity<?> saveEveryday(EveryPercentRequestDTO requestDTO) throws Exception{
        return budgetGuideService.saveEveryday(requestDTO);
    }

    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(EveryPercentRequestDTO requestDTO) throws Exception{
        return budgetGuideService.saveCategory(requestDTO);
    }
}
