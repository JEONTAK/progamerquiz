package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request.WhoAreYouRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request.WhoAreYouSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.WhoAreYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.WhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.service.WheAreYouService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("leagueoflegends/whoareyou")
public class WhoAreYouController {

    private final WheAreYouService wheareyouService;

    @PostMapping("/start")
    public ResponseEntity<WhoAreYouResponse> startWhoareyou() {
        log.info("[Who Are You] Set Quiz...");
        WhoAreYouResponse response = wheareyouService.startQuiz();
        log.info("[Who Are You] Finish Set Quiz...");
        log.info("[Who Are You] Answer : " + response.getAnswer().getProgamerTag());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<WhoAreYouSummitAnswerResponse> submitAnswer(@RequestBody WhoAreYouSubmitAnswerRequest request) {
        log.info("[Who Are You] Submitting answer: " + request.getInput());
        WhoAreYouSummitAnswerResponse response = wheareyouService.submitAnswer(
                request.getInput(),
                request.getAttempts(),
                request.getAnswer(),
                request.getGuessedList()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveResult")
    public ResponseEntity<Void> saveResult(@RequestBody WhoAreYouRequest request) {
        log.info("[Who Are You] Finish Quiz Result : " + request.getAttempts() + " / " + request.isCorrect());
        wheareyouService.saveResult(request.getId(), request.getAttempts(),request.isCorrect());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
