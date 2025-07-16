package pq.progamerquiz.domain.valorant.quizzes.igotyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouSubmitAnswerRequest;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.service.ValorantIGotYouService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/igotyou")
public class ValorantGotYouController {

    private final ValorantIGotYouService iGotYouService;

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<ValorantIGotYouResponse> setQuiz(@RequestBody LOLIGotYouStartRequest request) {
        log.info("[I Got You] Request size : " + request.getTotalQuizCount());
        log.info("[I Got You] Set Quiz...");
        List<ValorantIGotYouQuizResponse> quizList = iGotYouService.setQuizLists(request.getTotalQuizCount());
        log.info("[I Got You] Finish Set Quiz...");
        for (ValorantIGotYouQuizResponse iGotYouQuizResponse : quizList) {
            log.info("[I Got You] " +  iGotYouQuizResponse.getIndex() + " : " + iGotYouQuizResponse.getProgamerTag());
            for (int i = 0; i < iGotYouQuizResponse.getTeams().size(); i++) {
                log.info("[I Got You]      Team : " + iGotYouQuizResponse.getTeams().get(i).getCallName() + " (" + iGotYouQuizResponse.getTeams().get(i).getSeasonYear() +")");
            }
        }
        ValorantIGotYouResponse response = iGotYouService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<ValorantIGotYouSubmitAnswerResponse> submitAnswer(@RequestBody LOLIGotYouSubmitAnswerRequest request) {
        log.info("[I Got You] Submitting answer: " + request.getInput());
        ValorantIGotYouSubmitAnswerResponse response = iGotYouService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<ValorantIGotYouResultResponse> quizEnd(@RequestBody LOLIGotYouSaveResultRequest request) {
        log.info("[I Got You] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        ValorantIGotYouResultResponse response = iGotYouService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}