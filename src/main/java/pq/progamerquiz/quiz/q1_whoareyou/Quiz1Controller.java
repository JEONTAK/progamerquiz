package pq.progamerquiz.quiz.q1_whoareyou;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
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
    private ProgamerDto answer;
    private int attempts = 1;
    private static final int MAX_ATTEMPTS = 8;
    private List<Progamer> guessedList = new ArrayList<>();

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Make Answer Progamer");
        answer = quiz1Service.getRandomProgamer();
        String imagePath = quiz1Service.getImagePath(answer);
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);
        return "redirect:/whoareyou/" + answer.getId();
    }

    @Transactional
    @GetMapping("/{progamerId}")
    public String showQuiz(@PathVariable Long progamerId, Model model, HttpSession session) {
        ProgamerDto progamerDto = quiz1Service.findProgamerDtoById(progamerId);

        String imagePath = quiz1Service.getImagePath(progamerDto);

        // 모델에 데이터 추가
        model.addAttribute("answer", progamerDto);
        model.addAttribute("imagePath", imagePath);

        // 세션에서 정답 여부와 관련된 정보 가져오기
        Integer tryStatus = (Integer) session.getAttribute("try");
        List<ProgamerDto> guessedList = (List<ProgamerDto>) session.getAttribute("guessedList");
        Integer attempts = (Integer) session.getAttribute("attempts");
        model.addAttribute("try", tryStatus);
        model.addAttribute("guessedList", guessedList);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);

        log.info("Progamer: " + progamerDto);  // progamer가 null인지 확인
        log.info("Image Path: " + imagePath);
        log.info("Try status: " + tryStatus);
        log.info("Guessed List: " + guessedList);
        log.info("Attempts: " + attempts);
        // 세션 정보 초기화
        session.removeAttribute("try");
        session.removeAttribute("guessedList");
        session.removeAttribute("attempts");

        return "quizzes/whoareyou";
    }

    @PostMapping("/{progamerId}")
    public String submitAnswer(@RequestParam("input") String submit, Model model, HttpSession session) {
        log.info(submit);
        Progamer submitProgamer = quiz1Service.findByPid(submit);

        if (submitProgamer == null) {
            log.error("Submitted progamer is null");
            return "redirect:/whoareyou/" + answer.getId();
        }

        log.info(submitProgamer.getId() + " " + submitProgamer.getPid());

        if (submitProgamer.getId().equals(answer.getId())) {
            session.setAttribute("try", 1);
            session.setAttribute("guessedList", guessedList);
            session.setAttribute("attempts", attempts);
            log.info("Correct answer!");
        } else {
            session.setAttribute("try", 0);
            attempts++;
            guessedList.add(submitProgamer);
            session.setAttribute("guessedList", guessedList);
            session.setAttribute("attempts", attempts);
            log.info("Incorrect answer.");
        }

        return "redirect:/whoareyou/" + answer.getId();
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
