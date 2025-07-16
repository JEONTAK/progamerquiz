package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request.LOLWhoAreYouRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request.LOLWhoAreYouSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.LOLWhoAreYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.LOLWhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.service.LOLWhoAreYouService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("leagueoflegends/whoareyou")
public class LOLWhoAreYouController {

    private final LOLWhoAreYouService lolWhoAreYouService;

    @PostMapping("/startQuiz")
    public ResponseEntity<LOLWhoAreYouResponse> startWhoareyou() {
        log.info("[Who Are You] Set Quiz...");
        LOLWhoAreYouResponse response = lolWhoAreYouService.startQuiz();
        log.info("[Who Are You] Finish Set Quiz...");
        log.info("[Who Are You] Answer : " + response.getAnswer().getProgamerTag());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<LOLWhoAreYouSummitAnswerResponse> submitAnswer(@RequestBody LOLWhoAreYouSubmitAnswerRequest request) {
        log.info("[Who Are You] Submitting answer: " + request.getInput());
        LOLWhoAreYouSummitAnswerResponse response = lolWhoAreYouService.submitAnswer(
                request.getInput(),
                request.getAttempts(),
                request.getAnswer(),
                request.getGuessedList()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveResult")
    public ResponseEntity<Void> saveResult(@RequestBody LOLWhoAreYouRequest request) {
        log.info("[Who Are You] Finish Quiz Result : " + request.getAttempts() + " / " + request.isCorrect());
        lolWhoAreYouService.saveResult(request.getId(), request.getAttempts(),request.isCorrect());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
