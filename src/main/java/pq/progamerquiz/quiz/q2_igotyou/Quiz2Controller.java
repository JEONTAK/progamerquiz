package pq.progamerquiz.quiz.q2_igotyou;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Dto;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Quiz : I Got you!
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/igotyou")
public class Quiz2Controller {

    @Autowired
    private Quiz2Service quiz2Service;
    private String isSubmitted;
    private String isCorrect;
    private int totalCount;
    private int correctCount;
    private int currentIndex;
    private List<Quiz2Dto> quizList = new ArrayList<>();

    private void initialize() {
        isSubmitted = "true";
        isCorrect = "start";
        totalCount = 0;
        correctCount = 0;
        currentIndex = 0;
        quizList.clear();
    }

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Start Quiz2 I got You!");
        initialize();
        return "redirect:/igotyou/quiz";
    }

    @GetMapping("/quiz")
    public String gettingQuiz(@RequestParam(value = "currentIndex", required = false) Integer cIdx, Model model) {
        log.info("Current Index: " + cIdx);
        currentIndex = cIdx == null ? 0 : cIdx;
        model.addAttribute("quizList", quizList);
        model.addAttribute("isSubmitted", isSubmitted);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentIndex", currentIndex);
        return "quizzes/igotyou";
    }

    @GetMapping("/quiz/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getQuiz(@RequestParam(value = "currentIndex", required = false) Integer currentIndex) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSubmitted", isSubmitted);
        result.put("isCorrect", isCorrect);
        result.put("currentIndex", currentIndex);
        result.put("correctCount", correctCount);
        result.put("totalCount", totalCount);
        result.put("currentPlayer", quizList.get(currentIndex));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, String> payload, Model model) {
        String userInput = payload.get("input");
        log.info("Submitting answer: " + userInput);
        Map<String, Object> response = new HashMap<>();
        if (quiz2Service.isExist(userInput)) {
            isSubmitted = "true";

            if (quiz2Service.isAnswer(userInput, quizList.get(currentIndex))) {
                isCorrect = "true";
                correctCount++;
            } else {
                isCorrect = "false";
            }
        } else {
            isSubmitted = "false";
            isCorrect = "none";
        }
        response.put("isSubmitted", isSubmitted);
        response.put("isCorrect", isCorrect);
        response.put("correctCount", correctCount);
        response.put("currentPlayer", quizList.get(currentIndex));
        response.put("totalCount", totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("correctCount", correctCount);

        log.info("isSubmitted: " + isSubmitted);
        log.info("isCorrect: " + isCorrect);
        log.info("currentIndex: " + currentIndex);
        log.info("correctCount: " + correctCount);
        log.info("totalCount: " + totalCount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<?> setQuiz(@RequestBody Map<String, Integer> payload) {
        initialize();
        totalCount = payload.get("totalCount");
        log.info("Request size : " + totalCount);
        log.info("Set Progamers...");
        quizList = quiz2Service.getProgamers(totalCount);
        log.info("Start Quiz2 I got You!");
        for (Quiz2Dto quiz2Dto : quizList) {
            log.info(quiz2Dto.getIndex() + " : " + quiz2Dto.getPid());
            for (int i = 0; i < quiz2Dto.getTeamNames().size(); i++) {
                log.info("    Team : " + quiz2Dto.getTeamNames().get(i) + " (" + quiz2Dto.getTeamYears().get(i) +")");
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("quizList", quizList);
        response.put("correctCount", correctCount);
        response.put("totalCount", totalCount);
        return ResponseEntity.ok(response);
    }


    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @GetMapping("/end")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> quizEnd() {
        Map<String, Object> result = new HashMap<>();
        result.put("correctCount", correctCount);
        result.put("totalCount", totalCount);
        return new ResponseEntity<>(result, HttpStatus.OK); // 결과를 JSON 형태로 반환
    }
}

