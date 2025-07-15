package pq.progamerquiz.domain.valorant.quizzes.whoareyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("valorant/whoareyou/start")
public class ValorantWhoAreYouRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/valorant/whoareyou";
    }
}
