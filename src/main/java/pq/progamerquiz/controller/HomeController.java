package pq.progamerquiz.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.domain.leagueoflegends.quiz.dto.response.QuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quiz.entity.Quiz;
import pq.progamerquiz.domain.leagueoflegends.quiz.service.QuizService;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ResourceLoader resourceLoader;
    private final QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("Main Page");
        List<Game> games = Arrays.stream(Game.values()).toList();

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
        model.addAttribute("games", games);
        return "home";
    }

}
