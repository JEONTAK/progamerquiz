package pq.progamerquiz.domain.quizzes.igotyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.quizzes.igotyou.dto.request.IGotYouSaveResultRequest;
import pq.progamerquiz.domain.quizzes.igotyou.dto.request.IGotYouStartRequest;
import pq.progamerquiz.domain.quizzes.igotyou.dto.request.IGotYouSubmitAnswerRequest;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouQuizResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouResultResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.quizzes.igotyou.service.IGotYouService;

import java.util.List;

;

//Quiz : I Got you!
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/igotyou")
public class IGotYouController {

    private final IGotYouService iGotYouService;

    @GetMapping
    public String renderQuizPage() {
        log.info("I got You!");
        return "quizzes/igotyou"; // Thymeleaf 또는 정적 페이지를 반환
    }

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<IGotYouResponse> setQuiz(@RequestBody IGotYouStartRequest request) {
        log.info("Request size : " + request.getTotalQuizCount());
        log.info("Set Quiz...");
        List<IGotYouQuizResponse> quizList = iGotYouService.setQuizLists(request.getTotalQuizCount());
        log.info("Finish Set Quiz...");
        for (IGotYouQuizResponse IGotYouQuizResponse : quizList) {
            log.info(IGotYouQuizResponse.getIndex() + " : " + IGotYouQuizResponse.getProgamerTag());
            for (int i = 0; i < IGotYouQuizResponse.getTeams().size(); i++) {
                log.info("      Team : " + IGotYouQuizResponse.getTeams().get(i).getCallName() + " (" + IGotYouQuizResponse.getTeams().get(i).getSeasonYear() +")");
            }
        }
        IGotYouResponse response = iGotYouService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<IGotYouSubmitAnswerResponse> submitAnswer(@RequestBody IGotYouSubmitAnswerRequest request) {
        log.info("Submitting answer: " + request.getInput());
        IGotYouSubmitAnswerResponse response = iGotYouService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<IGotYouResultResponse> quizEnd(@RequestBody IGotYouSaveResultRequest request) {
        log.info("Finish Quiz (I Got you!) Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        IGotYouResultResponse response = iGotYouService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}

