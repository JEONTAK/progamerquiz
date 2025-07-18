package pq.progamerquiz.domain.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WhoAreYouRenderController {

    @GetMapping("/whoareyou")
    public String renderQuizPage() {
        return "quizzes/whoareyou";
    }

    @GetMapping("/leagueoflegends/whoareyou/start")
    public String startLOLQuiz() {
        return "quizzes/leagueoflegends/whoareyou";
    }

    @GetMapping("/valorant/whoareyou/start")
    public String startValorantQuiz() {
        return "quizzes/valorant/whoareyou";
    }
}
