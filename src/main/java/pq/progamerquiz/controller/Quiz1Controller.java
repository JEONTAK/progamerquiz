package pq.progamerquiz.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz1Dto;
import pq.progamerquiz.service.Quiz1Service;

import java.util.*;

//Quiz : Who are you?
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/whoareyou")
public class Quiz1Controller {

    @Autowired
    private Quiz1Service quiz1Service;
    private Quiz1Dto answer;
    private String imagePath;
    private String isSubmitted;
    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 8;
    private List<Quiz1Dto> guessedList = new ArrayList<>();

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Who Are you?");
        initialize();
        log.info("Set Quiz...");
        answer = quiz1Service.getRandomProgamer();
        log.info("Finish Set Quiz...");
        imagePath = quiz1Service.getImagePath(answer);
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);
        log.info("Answer : " + answer.getId() + " / " + answer.getPid() + " / " + answer.getName());
        return "redirect:/whoareyou/" + answer.getId();
    }

    private void initialize() {
        isSubmitted = "true";
        attempts = 0;
        guessedList.clear();
    }

    @Transactional
    @GetMapping("/{progamerId}")
    public String showQuiz(Model model, HttpSession session) {
        // 모델에 데이터 추가
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);

        Integer tryStatus = (Integer) session.getAttribute("try");
        List<Quiz1Dto> guessedList = (List<Quiz1Dto>) session.getAttribute("guessedList");
        Integer attempts = (Integer) session.getAttribute("attempts");
        model.addAttribute("isSubmitted", isSubmitted);
        model.addAttribute("try", tryStatus);
        model.addAttribute("guessedList", guessedList);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);


        log.info("isSubmitted : " + isSubmitted
        + "\nTry status : " + tryStatus
        + "\nGuessed List : " + guessedList
        + "\nAttempts: " + attempts);
        // 세션 정보 초기화
        session.removeAttribute("try");
        session.removeAttribute("guessedList");
        session.removeAttribute("attempts");

        return "quizzes/whoareyou";
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        String userInput = payload.get("input");
        Optional<ProgamerDto> submitProgamer = quiz1Service.findByPid(userInput);
        if (submitProgamer.isPresent()) {

            Quiz1Dto curProgamer = Quiz1Dto.convert(submitProgamer);
            log.info("Submit Progamer: " + curProgamer.getId() + " / " + curProgamer.getPid());
            guessedList.add(curProgamer);

            if (curProgamer.getId().equals(answer.getId())) {
                response.put("isCorrect", "true");
                log.info("Correct answer!");
            } else {
                attempts++;
                if (attempts == 7) {
                    response.put("isCorrect", "end");
                    log.info("Finish quiz.");
                }else{
                    response.put("isCorrect", "false");
                    log.info("Incorrect answer.");
                }
            }
            response.put("isSubmitted", "true");
            response.put("guessedList", guessedList);
            response.put("attempts", attempts);
        } else {
            response.put("isSubmitted", "false");
            log.info("Submitted progamer is null");
        }
        return ResponseEntity.ok(response);
    }
}
