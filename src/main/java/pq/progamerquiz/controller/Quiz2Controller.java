package pq.progamerquiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz2Dto;
import pq.progamerquiz.service.Quiz2Service;

import java.util.*;

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
    private List<Quiz2Dto> quizList;

    private void initialize() {
        isSubmitted = "true";
        isCorrect = "start";
        totalCount = 0;
        correctCount = 0;
        currentIndex = 0;
        quizList = new ArrayList<>();
    }

    @GetMapping
    public String startQuiz() {
        log.info("I got You!");
        initialize();
        return "redirect:/igotyou/quiz";
    }

    @GetMapping("/quiz")
    public String gettingQuiz(@RequestParam(value = "currentIndex", required = false) Integer cIdx, Model model) {
        currentIndex = cIdx == null ? 0 : cIdx;
        model.addAttribute("quizList", quizList);
        model.addAttribute("isSubmitted", isSubmitted);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentIndex", currentIndex);

        if (!quizList.isEmpty() && quizList != null) {
            log.info("Current : " + currentIndex + " / "
                    + quizList.get(currentIndex).getName() + " / " + quizList.get(currentIndex).getPid());
        }
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
        Optional<ProgamerDto> submitProgamer = quiz2Service.findByPid(userInput);
        if (!submitProgamer.isEmpty()) {
            isSubmitted = "true";
            if (quiz2Service.isAnswer(submitProgamer, quizList.get(currentIndex))) {
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

        log.info("isSubmitted: " + isSubmitted
        + "\nisCorrect: " + isCorrect
        + "\ncurrentIndex: " + currentIndex
        + "\ncorrectCount: " + correctCount
        + "\ntotalCount: " + totalCount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<?> setQuiz(@RequestBody Map<String, Integer> payload) {
        initialize();
        totalCount = payload.get("totalCount");
        log.info("Request size : " + totalCount);
        log.info("Set Quiz...");
        quizList = quiz2Service.getProgamers(totalCount);
        log.info("Finish Set Quiz...");
        for (Quiz2Dto quiz2Dto : quizList) {
            log.info(quiz2Dto.getIndex() + " : " + quiz2Dto.getPid());
            for (int i = 0; i < quiz2Dto.getTeamNames().size(); i++) {
                log.info("      Team : " + quiz2Dto.getTeamNames().get(i) + " (" + quiz2Dto.getTeamYears().get(i) +")");
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
        log.info("Finish Quiz (I Got you!) Result : " + correctCount + " / " + totalCount);
        Map<String, Object> result = new HashMap<>();
        result.put("correctCount", correctCount);
        result.put("totalCount", totalCount);
        return new ResponseEntity<>(result, HttpStatus.OK); // 결과를 JSON 형태로 반환
    }
}

