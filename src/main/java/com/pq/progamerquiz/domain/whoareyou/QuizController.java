package com.pq.progamerquiz.domain.whoareyou;

import com.pq.progamerquiz.progamerinfo.ProGamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    private ProGamer correctGamer;
    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 8;

    @GetMapping("/")
    public String startQuiz(Model model) {
        correctGamer = quizService.getRandomProGamer();
        attempts = 0;
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);
        return "quiz";
    }

    @PostMapping("/guess")
    public String guessProGamer(@RequestParam String playerId, Model model) {
        attempts++;
        ProGamer guessedGamer = quizService.getProGamerByPlayerId(playerId);

        model.addAttribute("guessedGamer", guessedGamer);
        model.addAttribute("correctGamer", correctGamer);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);

        if (guessedGamer != null && guessedGamer.equals(correctGamer)) {
            model.addAttribute("correct", true);
        } else if (attempts >= MAX_ATTEMPTS) {
            model.addAttribute("gameOver", true);
        }

        return "quiz";
    }
}
