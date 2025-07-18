package pq.progamerquiz.domain.quizzes.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.domain.quizzes.entity.Quiz;
import pq.progamerquiz.domain.quizzes.service.QuizService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{quizUrl}")
    public String handleQuiz(@PathVariable String quizUrl, Model model) {
        List<Quiz> quizzes = quizService.findAll();
        for (Quiz quiz : quizzes) {
            System.out.println(quiz.getImageUrl());
        }
        model.addAttribute("quizzes", quizzes);
        return "redirect:/" + quizUrl; // quizzes 폴더의 quizUrl에 해당하는 뷰 파일로 이동
    }
}
