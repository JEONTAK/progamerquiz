package pq.progamerquiz.domain.quizzes.whichisteam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.request.WhichIsTeamSaveResultRequest;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.request.WhichIsTeamStartRequest;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.request.WhichIsTeamSubmitAnswerRequest;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamQuizResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamResultResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.service.WhichIsTeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/whichisteam")
public class WhichIsTeamController {

    private final WhichIsTeamService whichIsTeamService;

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<WhichIsTeamResponse> setQuiz(@RequestBody WhichIsTeamStartRequest request) {
        log.info("[Which Is Team] Request size : " + request.getTotalQuizCount());
        log.info("[Which Is Team] Set Quiz...");
        List<WhichIsTeamQuizResponse> quizList = whichIsTeamService.setQuizLists(request.getTotalQuizCount());
        log.info("[Which Is Team] Finish Set Quiz...");
        for (WhichIsTeamQuizResponse whichIsTeamQuizResponse : quizList) {
            log.info("[Which Is Team] " + whichIsTeamQuizResponse.getIndex() + " : " + whichIsTeamQuizResponse.getTeamName() + " : " + whichIsTeamQuizResponse.getSeasonYear());
            for (int i = 0; i < whichIsTeamQuizResponse.getRosters().size(); i++) {
                log.info("[Which Is Team]       Progamer : " + whichIsTeamQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        WhichIsTeamResponse response = whichIsTeamService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<WhichIsTeamSubmitAnswerResponse> submitAnswer(@RequestBody WhichIsTeamSubmitAnswerRequest request) {
        log.info("[Which Is Team] Submitting answer: " + request.getInput());
        WhichIsTeamSubmitAnswerResponse response = whichIsTeamService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<WhichIsTeamResultResponse> quizEnd(@RequestBody WhichIsTeamSaveResultRequest request) {
        log.info("[Which Is Team] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        WhichIsTeamResultResponse response = whichIsTeamService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}