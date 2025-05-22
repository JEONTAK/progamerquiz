package pq.progamerquiz.domain.whoareyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.domain.progamer.mapper.ProgamerMapper;
import pq.progamerquiz.domain.whoareyou.dto.request.WhoareyouRequest;
import pq.progamerquiz.domain.whoareyou.dto.request.WhoareyouSummitAnswerRequest;
import pq.progamerquiz.domain.whoareyou.dto.response.WhoareyouResponse;
import pq.progamerquiz.domain.whoareyou.dto.response.WhoareyouSummitAnswerResponse;
import pq.progamerquiz.domain.whoareyou.entity.Whoareyou;
import pq.progamerquiz.domain.whoareyou.service.WheareyouService;

import java.util.ArrayList;
import java.util.List;

//Quiz : Who are you?
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/whoareyou")
public class WhoareyouController {

    private final WheareyouService wheareyouService;
    private final ProgamerMapper progamerMapper;

    @GetMapping
    public String renderQuizPage() {
        log.info("Who Are you?");
        return "quizzes/whoareyou"; // Thymeleaf 또는 정적 페이지를 반환
    }

    @PostMapping("/start")
    public ResponseEntity<WhoareyouResponse> startWhoareyou() {
        Whoareyou whoareyou = wheareyouService.startQuiz();
        List<String> guessedList = new ArrayList<>();
        return ResponseEntity.ok(
                WhoareyouResponse.of(
                        whoareyou.getId(),
                        progamerMapper.toDto(whoareyou.getQuizProgamer()),
                        whoareyou.getAttempt(),
                        whoareyou.isCorrect(),
                        guessedList
                )
        );
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<WhoareyouSummitAnswerResponse> submitAnswer(@RequestBody WhoareyouSummitAnswerRequest request) {
        WhoareyouSummitAnswerResponse response = wheareyouService.submitAnswer(
                request.getInput(),
                request.getAttempts(),
                request.getAnswer(),
                request.getGuessedList()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveResult")
    public ResponseEntity<Void> saveResult(@RequestBody WhoareyouRequest request) {
        wheareyouService.saveResult(request.getId(), request.getAttempts(),request.isCorrect());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
