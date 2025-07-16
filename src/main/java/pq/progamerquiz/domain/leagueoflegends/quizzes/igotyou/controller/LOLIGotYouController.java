package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.request.LOLIGotYouSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.service.LOLIGotYouService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouResultResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leagueoflegends/igotyou")
public class LOLIGotYouController {

    private final LOLIGotYouService lolIGotYouService;

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<LOLIGotYouResponse> setQuiz(@RequestBody LOLIGotYouStartRequest request) {
        log.info("[I Got You] Request size : " + request.getTotalQuizCount());
        log.info("[I Got You] Set Quiz...");
        List<LOLIGotYouQuizResponse> quizList = lolIGotYouService.setQuizLists(request.getTotalQuizCount());
        log.info("[I Got You] Finish Set Quiz...");
        for (LOLIGotYouQuizResponse LOLIGotYouQuizResponse : quizList) {
            log.info("[I Got You] " +  LOLIGotYouQuizResponse.getIndex() + " : " + LOLIGotYouQuizResponse.getProgamerTag());
            for (int i = 0; i < LOLIGotYouQuizResponse.getTeams().size(); i++) {
                log.info("[I Got You]      Team : " + LOLIGotYouQuizResponse.getTeams().get(i).getCallName() + " (" + LOLIGotYouQuizResponse.getTeams().get(i).getSeasonYear() +")");
            }
        }
        LOLIGotYouResponse response = lolIGotYouService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<LOLIGotYouSubmitAnswerResponse> submitAnswer(@RequestBody LOLIGotYouSubmitAnswerRequest request) {
        log.info("[I Got You] Submitting answer: " + request.getInput());
        LOLIGotYouSubmitAnswerResponse response = lolIGotYouService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<LOLIGotYouResultResponse> quizEnd(@RequestBody LOLIGotYouSaveResultRequest request) {
        log.info("[I Got You] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        LOLIGotYouResultResponse response = lolIGotYouService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}

