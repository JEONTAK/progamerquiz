package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.PieceOfPuzzleSaveResultRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.PieceOfPuzzleStartRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request.PieceOfPuzzleSubmitAnswerRequest;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.service.PieceOfPuzzleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pieceofpuzzle")
public class PieceOfPuzzleController {

    private final PieceOfPuzzleService pieceOfPuzzleService;

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<PieceOfPuzzleResponse> setQuiz(@RequestBody PieceOfPuzzleStartRequest request) {
        log.info("[Piece Of Puzzle] Request size : " + request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Set Quiz...");
        List<PieceOfPuzzleQuizResponse> quizList = pieceOfPuzzleService.setQuizLists(request.getTotalQuizCount());
        log.info("[Piece Of Puzzle] Finish Set Quiz...");
        for (PieceOfPuzzleQuizResponse pieceOfPuzzleQuizResponse : quizList) {
            log.info("[Piece Of Puzzle] " + pieceOfPuzzleQuizResponse.getIndex() + " : " + pieceOfPuzzleQuizResponse.getAnswerProgamerTag() + " : " + pieceOfPuzzleQuizResponse.getSeasonYear());
            for (int i = 0; i < pieceOfPuzzleQuizResponse.getRosters().size(); i++) {
                log.info("[Piece Of Puzzle]       Progamer : " + pieceOfPuzzleQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        PieceOfPuzzleResponse response = pieceOfPuzzleService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<PieceOfPuzzleSubmitAnswerResponse> submitAnswer(@RequestBody PieceOfPuzzleSubmitAnswerRequest request) {
        log.info("[Piece Of Puzzle] Submitting answer: " + request.getInput());
        PieceOfPuzzleSubmitAnswerResponse response = pieceOfPuzzleService.submitAnswer(request.getId(), request.getIndex(), request.getCorrectQuizCount(), request.getTotalQuizCount(), request.getInput());
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @PostMapping("/end")
    @ResponseBody
    public ResponseEntity<PieceOfPuzzleResultResponse> quizEnd(@RequestBody PieceOfPuzzleSaveResultRequest request) {
        log.info("[Piece Of Puzzle] Finish Quiz Result : " + request.getCorrectQuizCount() + " / " + request.getTotalQuizCount());
        PieceOfPuzzleResultResponse response = pieceOfPuzzleService.saveResult(request.getId(), request.getCorrectQuizCount(), request.getTotalQuizCount());
        return ResponseEntity.ok(response);
    }
}