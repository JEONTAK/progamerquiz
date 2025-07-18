package pq.progamerquiz.domain.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IGotYouRenderController {

    @GetMapping("/igotyou")
    public String renderQuizPage() {
        return "quizzes/igotyou";
    }

    @GetMapping("/leagueoflegends/igotyou/start")
    public String startLOLQuiz() {
        return "quizzes/leagueoflegends/igotyou";
    }

    @GetMapping("/valorant/igotyou/start")
    public String startValorantQuiz() {
        return "quizzes/valorant/igotyou";
    }
}

