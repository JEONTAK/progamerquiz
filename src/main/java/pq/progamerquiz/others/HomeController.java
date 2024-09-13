package pq.progamerquiz.others;


import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pq.progamerquiz.quiz.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final ResourceLoader resourceLoader;

    public HomeController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("Home Controller");

        List<Quiz> quizzes = Arrays.asList(
                new Quiz("whoareyou", "whoareyou.png", "Who Are You?", "Guess the Progamer."),
                new Quiz("progamerbingo", "progamerbingo.png", "Bingo!", "Make Bingo"),
                new Quiz("tictactoe", "tictactoe.png", "Tic Tac Toe", "Play Tic Tac Toe"),
                new Quiz("pieceofpuzzle", "pieceofpuzzle.png", "Piece of Puzzle",
                        "Find Progamer on particular team"),
                new Quiz("whatisteam", "whatisteam.png", "What is Team?",
                        "Find Team Using Hint"),
                new Quiz("igotyou", "igotyou.png", "I Got You!", "Find Progamer Using Team")
        );
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
        }

        for (Quiz quiz : quizzes) {
            System.out.println("quiz.getImageUrl() = " + quiz.getImageUrl());
        }
        model.addAttribute("quizzes", quizzes);
        return "home";
    }


}
