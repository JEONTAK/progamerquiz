package pq.progamerquiz.quiz;


import pq.progamerquiz.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
}
