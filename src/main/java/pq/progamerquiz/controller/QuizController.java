package pq.progamerquiz.controller;


import pq.progamerquiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
}
