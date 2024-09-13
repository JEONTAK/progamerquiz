package pq.progamerquiz.quiz;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pq.progamerquiz.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{quizId}")
    public String requestQuiz(@PathVariable String quizId, Model model) {
        return "quizzes/" + quizId;
    }
}
