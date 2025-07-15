package pq.progamerquiz.domain.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/whoareyou")
public class WhoAreYouRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/whoareyou";
    }
}
