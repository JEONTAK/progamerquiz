package pq.progamerquiz.domain.valorant.quizzes.whichisteam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request.LOLWhichIsTeamSubmitAnswerRequest;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.service.ValorantWhichIsTeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/whichisteam")
public class ValorantWhichIsTeamController {

    private final ValorantWhichIsTeamService whichIsTeamService;

    @PostMapping("/startQuiz")
    @ResponseBody
    public ResponseEntity<ValorantWhichIsTeamResponse> setQuiz(@RequestBody LOLWhichIsTeamStartRequest request) {
        log.info("[Which Is Team] Request size : " + request.getTotalQuizCount());
        log.info("[Which Is Team] Set Quiz...");
        List<ValorantWhichIsTeamQuizResponse> quizList = whichIsTeamService.setQuizLists(request.getTotalQuizCount(), Game.Valorant);
        log.info("[Which Is Team] Finish Set Quiz...");
        for (ValorantWhichIsTeamQuizResponse whichIsTeamQuizResponse : quizList) {
            log.info("[Which Is Team] " + whichIsTeamQuizResponse.getIndex() + " : " + whichIsTeamQuizResponse.getTeamName() + " : " + whichIsTeamQuizResponse.getSeasonYear());
            for (int i = 0; i < whichIsTeamQuizResponse.getRosters().size(); i++) {
                log.info("[Which Is Team]       Progamer : " + whichIsTeamQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        ValorantWhichIsTeamResponse response = whichIsTeamService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<ValorantWhichIsTeamSubmitAnswerResponse> submitAnswer(@RequestBody LOLWhichIsTeamSubmitAnswerRequest request) {
        log.info("[Which Is Team] Submitting answer: " + request.getInput());
        ValorantWhichIsTeamSubmitAnswerResponse response = whichIsTeamService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<ValorantWhichIsTeamResultResponse> quizEnd(@RequestBody LOLWhichIsTeamSaveResultRequest request) {
        log.info("[Which Is Team] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        ValorantWhichIsTeamResultResponse response = whichIsTeamService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}