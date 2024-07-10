package com.pq.progamerquiz.domain.whoareyou;

import com.pq.progamerquiz.progamerinfo.ProGamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Quiz1Controller {

    @Autowired
    private Quiz1Service quiz1Service;

    private ProGamer correctGamer;
    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 8;
    private List<ProGamer> guessedList = new ArrayList<>();

    @GetMapping("/")
    public String startQuiz(Model model) {
        correctGamer = quiz1Service.getRandomProGamer();
        attempts = 0;
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);
        return "whoareyou";
    }

    @PostMapping("/")
    public String guessProGamer(@RequestParam String idOrName, Model model) {
        attempts++;
        ProGamer guessedGamer = quiz1Service.getProGamer(idOrName);
        guessedGamer.setAgeAndImage();
        guessedList.add(guessedGamer);
        model.addAttribute("guessedList", guessedList);
        model.addAttribute("correctGamer", correctGamer);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);

        if (guessedGamer != null && guessedGamer.equals(correctGamer)) {
            model.addAttribute("correct", true);
        } else if (attempts >= MAX_ATTEMPTS) {
            model.addAttribute("gameOver", true);
        }

        return "whoareyou";
    }
}
