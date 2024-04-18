package mackerel.dontworry.budgetguide.service;

import lombok.RequiredArgsConstructor;
import mackerel.dontworry.accountbook.repository.AccountBookRepository;
import mackerel.dontworry.budgetguide.dto.EveryPercentRequestDTO;
import mackerel.dontworry.user.domain.User;
import mackerel.dontworry.user.exception.NotFoundMemberException;
import mackerel.dontworry.user.repository.UserRepository;
import mackerel.dontworry.schedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetGuideService {
    private final UserRepository userRepository;
    private final AccountBookRepository accountBookRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ResponseEntity<?> savePercent(EveryPercentRequestDTO requestDTO) throws Exception{
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        return ResponseEntity.ok(requestDTO);
    }

    @Transactional
    public ResponseEntity<?> saveEveryday(EveryPercentRequestDTO requestDTO) throws Exception{
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
        return ResponseEntity.ok(requestDTO);
    }

    @Transactional
    public ResponseEntity<?> saveCategory(EveryPercentRequestDTO requestDTO) throws Exception{
        User user = userRepository.findByEmail(requestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + requestDTO.getUsername()));
//
//                Outfit outfit = outfitRequestDTO.toEntity(user);
//                outfit.setImgUrl(filePath);
//                outfitRepository.save(outfit);
        return ResponseEntity.ok(requestDTO);
    }
}
