package pq.progamerquiz.others;


import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pq.progamerquiz.quiz.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pq.progamerquiz.quiz.QuizService;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final ResourceLoader resourceLoader;
    private final EntityManager em;
    private final QuizService quizService;

    public HomeController(ResourceLoader resourceLoader, EntityManager em, QuizService quizService) {
        this.resourceLoader = resourceLoader;
        this.em = em;
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("Home Controller");
        List<Quiz> quizzes  = em.createQuery("select q from Quiz q", Quiz.class).getResultList();

        for (Quiz quiz : quizzes) {
            Resource image = resourceLoader.getResource("classpath:/static/images/quiz/" + quiz.getImageUrl());
            String imageUrl;
            if (image.exists() && image.isReadable()) {
                imageUrl = "/images/quiz/" + quiz.getImageUrl();
                quiz.setImageUrl(imageUrl);
            } else {
                // 이미지가 없으면 none.png 반환
                imageUrl = "/images/none.png";
                quiz.setImageUrl(imageUrl);
            }

            quizService.saveQuiz(quiz);
        }
        model.addAttribute("quizzes", quizzes);
        return "home";
    }


}
