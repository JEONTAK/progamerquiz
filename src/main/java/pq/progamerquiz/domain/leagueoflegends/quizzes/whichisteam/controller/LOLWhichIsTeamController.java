package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.service.LOLWhichIsTeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leagueoflegends/whichisteam")
public class LOLWhichIsTeamController {

    private final LOLWhichIsTeamService lolWhichIsTeamService;

    @PostMapping("/startQuiz")
    @ResponseBody
    public ResponseEntity<LOLWhichIsTeamResponse> setQuiz(@RequestBody LOLWhichIsTeamStartRequest request) {
        log.info("[Which Is Team] Request size : " + request.getTotalQuizCount());
        log.info("[Which Is Team] Set Quiz...");
        List<LOLWhichIsTeamQuizResponse> quizList = lolWhichIsTeamService.setQuizLists(request.getTotalQuizCount(), Game.LOL);
        log.info("[Which Is Team] Finish Set Quiz...");
        for (LOLWhichIsTeamQuizResponse LOLWhichIsTeamQuizResponse : quizList) {
            log.info("[Which Is Team] " + LOLWhichIsTeamQuizResponse.getIndex() + " : " + LOLWhichIsTeamQuizResponse.getTeamName() + " : " + LOLWhichIsTeamQuizResponse.getSeasonYear());
            for (int i = 0; i < LOLWhichIsTeamQuizResponse.getRosters().size(); i++) {
                log.info("[Which Is Team]       Progamer : " + LOLWhichIsTeamQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        LOLWhichIsTeamResponse response = lolWhichIsTeamService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<LOLWhichIsTeamSubmitAnswerResponse> submitAnswer(@RequestBody LOLWhichIsTeamSubmitAnswerRequest request) {
        log.info("[Which Is Team] Submitting answer: " + request.getInput());
        LOLWhichIsTeamSubmitAnswerResponse response = lolWhichIsTeamService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<LOLWhichIsTeamResultResponse> quizEnd(@RequestBody LOLWhichIsTeamSaveResultRequest request) {
        log.info("[Which Is Team] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        LOLWhichIsTeamResultResponse response = lolWhichIsTeamService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}