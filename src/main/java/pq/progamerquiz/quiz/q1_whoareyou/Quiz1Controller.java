package pq.progamerquiz.quiz.q1_whoareyou;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/whoareyou")
public class Quiz1Controller {

    @Autowired
    private Quiz1Service quiz1Service;
    private Progamer answer;
    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 8;
    private List<Progamer> guessedList = new ArrayList<>();
    @Autowired
    private ProgamerService progamerService;

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Make Answer Progamer");
        answer = quiz1Service.getRandomProgamer();
        String imagePath = quiz1Service.getImagePath(answer);
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);
        return "redirect:/whoareyou/" + answer.getId();
    }

    @GetMapping("/{progamerId}")
    public String showQuiz(@PathVariable Long progamerId, Model model) {
        // 전달된 선수 ID로 데이터를 가져옴
        Optional<Progamer> progamer = quiz1Service.findById(progamerId);
        String imagePath = quiz1Service.getImagePath(progamer.orElse(null));

        // 모델에 데이터 추가
        model.addAttribute("answer", progamer);
        model.addAttribute("imagePath", imagePath);

        // whoareyou.html 페이지로 이동
        return "quizzes/whoareyou";
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<String> submitAnswer(@RequestBody Map<String, String> payload) {
        String submittedAnswer = payload.get("answer");
        // 제출된 답변을 처리
        if (quiz1Service.checkAnswer(submittedAnswer)) {
            return ResponseEntity.ok("Correct Answer");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Answer");
        }
    }


 /*   @PostMapping("/{playerId}")
    public String getAnswer(@RequestParam @PathVariable String progamerId, Model model) {
        log.info("User give the answer" + )
    }*/

    /*

    @GetMapping("/quiz1")
    public String startQuiz(Model model) {
        correctGamer = quiz1Service.getRandomProGamer();
        attempts = 0;
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);
        return "quiz1";
    }

    @PostMapping("/quiz1")
    public String guessProGamer(@RequestParam String idOrName, Model model) {
        attempts++;
        Progamer guessedGamer = quiz1Service.getProGamer(idOrName);
        guessedGamer.setImage();
        guessedList.add(guessedGamer);
        model.addAttribute("guessedList", guessedList);
        model.addAttribute("correctGamer", correctGamer);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);

        if (guessedGamer != null && guessedGamer.equals(correctGamer)) {
            model.addAttribute("correct", true);
        } else if (attempts >= MAX_ATTEMPTS) {
            model.addAttribute("gameOver", true);
        }

        return "quiz1";
    }*/
}
