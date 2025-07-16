package pq.progamerquiz.domain.valorant.quizzes.whoareyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.request.ValorantWhoAreYouRequest;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.request.ValorantWhoAreYouSubmitAnswerRequest;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response.ValorantWhoAreYouResponse;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response.ValorantWhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.service.ValorantWhoAreYouService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/whoareyou")
public class ValorantWhoAreYouController {

    private final ValorantWhoAreYouService valorantWhoAreYouService;

    @PostMapping("/startQuiz")
    public ResponseEntity<ValorantWhoAreYouResponse> startWhoareyou() {
        log.info("[Who Are You] Set Quiz...");
        ValorantWhoAreYouResponse response = valorantWhoAreYouService.startQuiz();
        log.info("[Who Are You] Finish Set Quiz...");
        log.info("[Who Are You] Answer : " + response.getAnswer().getProgamerTag());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<ValorantWhoAreYouSummitAnswerResponse> submitAnswer(@RequestBody ValorantWhoAreYouSubmitAnswerRequest request) {
        log.info("[Who Are You] Submitting answer: " + request.getInput());
        ValorantWhoAreYouSummitAnswerResponse response = valorantWhoAreYouService.submitAnswer(
                request.getInput(),
                request.getAttempts(),
                request.getAnswer(),
                request.getGuessedList()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveResult")
    public ResponseEntity<Void> saveResult(@RequestBody ValorantWhoAreYouRequest request) {
        log.info("[Who Are You] Finish Quiz Result : " + request.getAttempts() + " / " + request.isCorrect());
        valorantWhoAreYouService.saveResult(request.getId(), request.getAttempts(),request.isCorrect());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
