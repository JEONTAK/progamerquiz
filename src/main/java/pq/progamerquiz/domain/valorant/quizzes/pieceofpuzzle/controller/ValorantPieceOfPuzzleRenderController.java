package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/pieceofpuzzle/start")
public class ValorantPieceOfPuzzleRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/valorant/pieceofpuzzle";
    }

}