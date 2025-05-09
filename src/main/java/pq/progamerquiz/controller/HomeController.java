package pq.progamerquiz.controller;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pq.progamerquiz.domain.quiz.dto.response.QuizResponse;
import pq.progamerquiz.domain.quiz.entity.Quiz;
import pq.progamerquiz.domain.quiz.service.QuizService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ResourceLoader resourceLoader;
    private final EntityManager em;
    private final QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("Home Controller");
        List<Quiz> quizzes  = quizService.findAll();

        quizzes.forEach(quiz -> {
            Resource image = resourceLoader.getResource("classpath:/static/images/quiz/" + quiz.getImageUrl());
            // 이미지가 없으면 none.png 반환
            String imageUrl = image.exists() && image.isReadable() ? "/images/quiz/" + quiz.getImageUrl() : "/images/none.png";
            quiz.setImageUrl(imageUrl);
            quizService.saveQuiz(quiz);
        });

        List<QuizResponse> responses = quizzes.stream().map(quiz -> {
            return QuizResponse.of(
                    quiz.getId(),
                    quiz.getUrl(),
                    quiz.getImageUrl(),
                    quiz.getAltText(),
                    quiz.getTitle(),
                    quiz.getDescription()
            );
        }).toList();

        model.addAttribute("quizzes", responses);
        return "home";
    }

    /*@GetMapping("/")
    public ResponseEntity<List<QuizResponse>> home(Model model) {
        log.info("Home Controller");
        List<Quiz> quizzes = quizService.findAll();

        quizzes.forEach(quiz -> {
            Resource image = resourceLoader.getResource("classpath:/static/images/quiz/" + quiz.getImageUrl());
            // 이미지가 없으면 none.png 반환
            String imageUrl = image.exists() && image.isReadable() ? "/images/quiz/" + quiz.getImageUrl() : "/images/none.png";
            quiz.setImageUrl(imageUrl);
            quizService.saveQuiz(quiz);
        });

        List<QuizResponse> responses = quizzes.stream().map(quiz -> {
            return QuizResponse.of(
                    quiz.getId(),
                    quiz.getUrl(),
                    quiz.getImageUrl(),
                    quiz.getAltText(),
                    quiz.getTitle(),
                    quiz.getDescription()
            );
        }).toList();
        return ResponseEntity.ok(responses);
    }*/


}
