package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.LOLPieceOfPuzzleSubmitAnswerRequest;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.service.ValorantPieceOfPuzzleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/pieceofpuzzle")
public class ValorantPieceOfPuzzleController {

    private final ValorantPieceOfPuzzleService pieceOfPuzzleService;

    @PostMapping("/startQuiz")
    @ResponseBody
    public ResponseEntity<ValorantPieceOfPuzzleResponse> setQuiz(@RequestBody LOLPieceOfPuzzleStartRequest request) {
        log.info("[Piece Of Puzzle] Request size : " + request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Set Quiz...");
        List<ValorantPieceOfPuzzleQuizResponse> quizList = pieceOfPuzzleService.setQuizLists(request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Finish Set Quiz...");
        for (ValorantPieceOfPuzzleQuizResponse pieceOfPuzzleQuizResponse : quizList) {
            log.info("[Piece Of Puzzle] " + pieceOfPuzzleQuizResponse.getIndex() + " : " + pieceOfPuzzleQuizResponse.getProgamerTag() + " : " + pieceOfPuzzleQuizResponse.getSeasonYear());
            for (int i = 0; i < pieceOfPuzzleQuizResponse.getRosters().size(); i++) {
                log.info("[Piece Of Puzzle]       Progamer : " + pieceOfPuzzleQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        ValorantPieceOfPuzzleResponse response = pieceOfPuzzleService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<ValorantPieceOfPuzzleSubmitAnswerResponse> submitAnswer(@RequestBody LOLPieceOfPuzzleSubmitAnswerRequest request) {
        log.info("[Piece Of Puzzle] Submitting answer: " + request.getInput());
        ValorantPieceOfPuzzleSubmitAnswerResponse response = pieceOfPuzzleService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<ValorantPieceOfPuzzleResultResponse> quizEnd(@RequestBody LOLPieceOfPuzzleSaveResultRequest request) {
        log.info("[Piece Of Puzzle] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        ValorantPieceOfPuzzleResultResponse response = pieceOfPuzzleService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}