package pq.progamerquiz.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.domain.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("Home Controller");
        List<String> menuItems = Arrays.asList("Who Are You?", "Bingo!", "Tic Tac Toe", "Piece of Puzzle",
                "What is Team?", "I Got You!");
        model.addAttribute("menuItems", menuItems);

        List<Quiz> quizzes = Arrays.asList(
                new Quiz("quiz1", "/media/content/quizImg/quiz1.jpg", "Who Are You?", "Guess the Progamer."),
                new Quiz("quiz2", "/media/content/quizImg/quiz2.jpg", "Bingo!", "Make Bingo"),
                new Quiz("quiz3", "/media/content/quizImg/quiz3.jpg", "Tic Tac Toe", "Play Tic Tac Toe"),
                new Quiz("quiz4", "/media/content/quizImg/quiz4.jpg", "Piece of Puzzle",
                        "Find Progamer on particular team"),
                new Quiz("quiz5", "/media/content/quizImg/quiz5.jpg", "What is Team?",
                        "Find Team Using Hint"),
                new Quiz("quiz6", "/media/content/quizImg/quiz6.jpg", "I Got You!", "Find Progamer Using Team")
        );
        model.addAttribute("quizzes", quizzes);

        return "home";
    }

   /* @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + file.getFullPath(filename));
    }*/
}
