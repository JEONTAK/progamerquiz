package pq.progamerquiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz1Dto;
import pq.progamerquiz.service.Quiz1Service;

import java.util.*;
import java.util.stream.Collectors;

//Quiz : Who are you?
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/whoareyou")
public class Quiz1Controller {

    @Autowired
    private Quiz1Service quiz1Service;
    private static final int MAX_ATTEMPTS = 8;
    @Autowired
    private ObjectMapper jacksonObjectMapper;


    @GetMapping
    public String renderQuizPage() {
        log.info("Who Are you?");
        return "quizzes/whoareyou"; // Thymeleaf 또는 정적 페이지를 반환
    }

    @PostMapping("/start")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> startQuiz() {

        log.info("Set Quiz...");
        Quiz1Dto answer = quiz1Service.getRandomProgamer();
        String imagePath = quiz1Service.getImagePath(answer);
        log.info("Finish Set Quiz...");
        log.info("Answer : " + answer.getId() + " / " + answer.getPid() + " / " + answer.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("answer", answer);
        response.put("imagePath", imagePath);
        response.put("attempts", 0);
        response.put("isCorrect", "start");
        response.put("guessedList", new ArrayList<>());
        response.put("maxAttempts", MAX_ATTEMPTS);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> payload) {

        String userInput = (String) payload.get("input");
        int attempts = payload.get("attempts") != null ? (int) payload.get("attempts") : 0; // 기본값 0
        Quiz1Dto answer = jacksonObjectMapper.convertValue(payload.get("answer"), Quiz1Dto.class);
        List<Quiz1Dto> guessedList = ((List<?>) payload.get("guessedList")).stream()
                .map(item -> jacksonObjectMapper.convertValue(item, Quiz1Dto.class))
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, Object> response = new HashMap<>();
        Optional<ProgamerDto> submitProgamer = quiz1Service.findByPid(userInput);
        if (submitProgamer.isPresent()) {
            Quiz1Dto curProgamer = Quiz1Dto.convert(submitProgamer);
            log.info("Submit Progamer: " + curProgamer.getId() + " / " + curProgamer.getPid());
            guessedList.add(curProgamer);
            log.info("GuessedList Size : " + guessedList.size());
            if (curProgamer.getId().equals(answer.getId())) {
                response.put("isCorrect", "true");
                log.info("Correct answer!");
            } else {
                attempts++;
                if (attempts >= 8) {
                    response.put("isCorrect", "end");
                    log.info("Finish quiz.");
                }else{
                    response.put("isCorrect", "false");
                    log.info("Incorrect answer.");
                }
            }
            response.put("isSubmitted", "true");
            response.put("progamer", curProgamer);
            response.put("attempts", attempts);
            response.put("answer", answer);
        } else {
            response.put("isSubmitted", "false");
            log.info("Submitted progamer is null");
        }

        return ResponseEntity.ok(response);
    }

/*    @Transactional
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
    }*/
}
