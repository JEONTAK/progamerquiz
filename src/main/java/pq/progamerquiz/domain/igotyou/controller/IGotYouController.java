package pq.progamerquiz.domain.igotyou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerResponse;
import pq.progamerquiz.domain.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.igotyou.service.IGotYouService;

import java.util.*;

//Quiz : I Got you!
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/igotyou")
public class IGotYouController {

    @Autowired
    private IGotYouService IGotYouService;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @GetMapping
    public String renderQuizPage() {
        log.info("I got You!");
        return "quizzes/igotyou"; // Thymeleaf 또는 정적 페이지를 반환
    }

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<?> setQuiz(@RequestBody Map<String, Integer> payload) {
        int totalCount = payload.get("totalCount");
        log.info("Request size : " + totalCount);
        log.info("Set Quiz...");
        List<IGotYouResponse> quizList = IGotYouService.getProgamers(totalCount);
        log.info("Finish Set Quiz...");
        for (IGotYouResponse IGotYouResponse : quizList) {
            log.info(IGotYouResponse.getIndex() + " : " + IGotYouResponse.getPid());
            for (int i = 0; i < IGotYouResponse.getTeamNames().size(); i++) {
                log.info("      Team : " + IGotYouResponse.getTeamNames().get(i) + " (" + IGotYouResponse.getTeamYears().get(i) +")");
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("quizList", quizList);
        response.put("correctCount", 0);
        response.put("totalCount", totalCount);
        response.put("currentIndex", 0);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/quiz")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> gettingQuiz(@RequestBody Map<String, Object> payload) {
        List<IGotYouResponse> quizList = ((List<?>) payload.get("quizList")).stream()
                .map(item -> jacksonObjectMapper.convertValue(item, IGotYouResponse.class))
                .toList();
        int currentIndex = (int) payload.get("currentIndex");
        log.info("Current : " + currentIndex + " / "
                + quizList.get(currentIndex).getName() + " / " + quizList.get(currentIndex).getPid());

        Map<String, Object> response = new HashMap<>();
        response.put("quizList", quizList);
        response.put("currentIndex", currentIndex);
        response.put("currentPlayer", quizList.get(currentIndex));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> payload) {
        String userInput = (String) payload.get("input");
        int currentIndex = (int) payload.get("currentIndex");
        int correctCount = (int) payload.get("correctCount");
        int totalCount = (int) payload.get("totalCount");
        List<IGotYouResponse> quizList = ((List<?>) payload.get("quizList")).stream()
                .map(item -> jacksonObjectMapper.convertValue(item, IGotYouResponse.class))
                .toList();
        String isSubmitted = "true";
        String isCorrect = "none";

        log.info("Submitting answer: " + userInput);
        Map<String, Object> response = new HashMap<>();
        Optional<ProgamerResponse> submitProgamer = IGotYouService.findByPid(userInput);
        if (!submitProgamer.isEmpty()) {
            isSubmitted = "true";
            if (IGotYouService.isAnswer(submitProgamer, quizList.get(currentIndex))) {
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
        response.put("currentIndex", ++currentIndex);

        log.info("isSubmitted: " + isSubmitted
                + "\nisCorrect: " + isCorrect
                + "\ncurrentIndex: " + currentIndex
                + "\ncorrectCount: " + correctCount
                + "\ntotalCount: " + payload.get("totalCount"));
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @GetMapping("/end")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> quizEnd(@RequestBody Map<String, Object> payload) {
        int correctCount = (int) payload.get("correctCount");
        int totalCount = (int) payload.get("totalCount");
        log.info("Finish Quiz (I Got you!) Result : " + correctCount + " / " + totalCount);
        Map<String, Object> result = new HashMap<>();
        result.put("correctCount", correctCount);
        result.put("totalCount", totalCount);
        return new ResponseEntity<>(result, HttpStatus.OK); // 결과를 JSON 형태로 반환
    }
}

