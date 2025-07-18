package pq.progamerquiz.domain.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WhichIsTeamRenderController {

    @GetMapping("/whichisteam")
    public String renderQuizPage() {
        return "quizzes/whichisteam";
    }

    @GetMapping("/leagueoflegends/whichisteam/start")
    public String startLOLQuiz() {
        return "quizzes/leagueoflegends/whichisteam";
    }

    @GetMapping("/valorant/whichisteam/start")
    public String startValorantQuiz() {
        return "quizzes/valorant/whichisteam";
    }
}