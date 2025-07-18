package pq.progamerquiz.domain.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PieceOfPuzzleRenderController {

    @GetMapping("/pieceofpuzzle")
    public String renderQuizPage() {
        return "quizzes/pieceofpuzzle";
    }

    @GetMapping("/leagueoflegends/pieceofpuzzle/start")
    public String startLOLQuiz() {
        return "quizzes/leagueoflegends/pieceofpuzzle";
    }

    @GetMapping("/valorant/pieceofpuzzle/start")
    public String startValorantQuiz() {
        return "quizzes/valorant/pieceofpuzzle";
    }
}