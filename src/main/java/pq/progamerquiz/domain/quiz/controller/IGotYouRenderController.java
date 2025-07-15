package pq.progamerquiz.domain.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/igotyou")
public class IGotYouRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/igotyou";
    }

}

