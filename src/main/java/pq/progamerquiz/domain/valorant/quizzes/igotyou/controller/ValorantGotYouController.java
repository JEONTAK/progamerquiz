package pq.progamerquiz.domain.valorant.quizzes.igotyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.IGotYouSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.IGotYouStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.IGotYouSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.service.IGotYouService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/igotyou")
public class ValorantGotYouController {

    private final IGotYouService iGotYouService;

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<IGotYouResponse> setQuiz(@RequestBody IGotYouStartRequest request) {
        log.info("[I Got You] Request size : " + request.getTotalQuizCount());
        log.info("[I Got You] Set Quiz...");
        List<IGotYouQuizResponse> quizList = iGotYouService.setQuizLists(request.getTotalQuizCount());
        log.info("[I Got You] Finish Set Quiz...");
        for (IGotYouQuizResponse iGotYouQuizResponse : quizList) {
            log.info("[I Got You] " +  iGotYouQuizResponse.getIndex() + " : " + iGotYouQuizResponse.getProgamerTag());
            for (int i = 0; i < iGotYouQuizResponse.getTeams().size(); i++) {
                log.info("[I Got You]      Team : " + iGotYouQuizResponse.getTeams().get(i).getCallName() + " (" + iGotYouQuizResponse.getTeams().get(i).getSeasonYear() +")");
            }
        }
        IGotYouResponse response = iGotYouService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<IGotYouSubmitAnswerResponse> submitAnswer(@RequestBody IGotYouSubmitAnswerRequest request) {
        log.info("[I Got You] Submitting answer: " + request.getInput());
        IGotYouSubmitAnswerResponse response = iGotYouService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<IGotYouResultResponse> quizEnd(@RequestBody IGotYouSaveResultRequest request) {
        log.info("[I Got You] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        IGotYouResultResponse response = iGotYouService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}