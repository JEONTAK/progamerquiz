package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.service.LOLPieceOfPuzzleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leagueoflegends/pieceofpuzzle")
public class LOLPieceOfPuzzleController {

    private final LOLPieceOfPuzzleService lolPieceOfPuzzleService;

    @PostMapping("/startQuiz")
    @ResponseBody
    public ResponseEntity<LOLPieceOfPuzzleResponse> setQuiz(@RequestBody LOLPieceOfPuzzleStartRequest request) {
        log.info("[Piece Of Puzzle] Request size : " + request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Set Quiz...");
        List<LOLPieceOfPuzzleQuizResponse> quizList = lolPieceOfPuzzleService.setQuizLists(request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Finish Set Quiz...");
        for (LOLPieceOfPuzzleQuizResponse LOLPieceOfPuzzleQuizResponse : quizList) {
            log.info("[Piece Of Puzzle] " + LOLPieceOfPuzzleQuizResponse.getIndex() + " : " + LOLPieceOfPuzzleQuizResponse.getAnswerProgamerTag() + " : " + LOLPieceOfPuzzleQuizResponse.getSeasonYear());
            for (int i = 0; i < LOLPieceOfPuzzleQuizResponse.getRosters().size(); i++) {
                log.info("[Piece Of Puzzle]       Progamer : " + LOLPieceOfPuzzleQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        LOLPieceOfPuzzleResponse response = lolPieceOfPuzzleService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<LOLPieceOfPuzzleSubmitAnswerResponse> submitAnswer(@RequestBody LOLPieceOfPuzzleSubmitAnswerRequest request) {
        log.info("[Piece Of Puzzle] Submitting answer: " + request.getInput());
        LOLPieceOfPuzzleSubmitAnswerResponse response = lolPieceOfPuzzleService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<LOLPieceOfPuzzleResultResponse> quizEnd(@RequestBody LOLPieceOfPuzzleSaveResultRequest request) {
        log.info("[Piece Of Puzzle] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        LOLPieceOfPuzzleResultResponse response = lolPieceOfPuzzleService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}