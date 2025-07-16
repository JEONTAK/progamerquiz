package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/leagueoflegends/pieceofpuzzle/start")
public class LOLPieceOfPuzzleRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/leagueoflegends/pieceofpuzzle";
    }

}

