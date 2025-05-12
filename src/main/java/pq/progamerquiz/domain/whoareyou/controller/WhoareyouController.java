package pq.progamerquiz.domain.whoareyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.domain.progamer.mapper.ProgamerMapper;
import pq.progamerquiz.domain.whoareyou.dto.request.WhoareyouSummitAnswerRequest;
import pq.progamerquiz.domain.whoareyou.dto.response.WhoareyouResponse;
import pq.progamerquiz.domain.whoareyou.dto.response.WhoareyouSummitAnswerResponse;
import pq.progamerquiz.domain.whoareyou.entity.Whoareyou;
import pq.progamerquiz.domain.whoareyou.service.WheareyouService;

import java.util.ArrayList;
import java.util.List;

//Quiz : Who are you?
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/whoareyou")
public class WhoareyouController {

    private final WheareyouService wheareyouService;
    private final ProgamerMapper progamerMapper;

    @GetMapping
    public String renderQuizPage() {
        log.info("Who Are you?");
        return "quizzes/whoareyou"; // Thymeleaf 또는 정적 페이지를 반환
    }

    @PostMapping("/start")
    public ResponseEntity<WhoareyouResponse> startWhoareyou() {
        Whoareyou whoareyou = wheareyouService.startQuiz();
        List<String> guessedList = new ArrayList<>();
        return ResponseEntity.ok(
                WhoareyouResponse.of(
                        whoareyou.getId(),
                        progamerMapper.toDto(whoareyou.getQuizProgamer()),
                        whoareyou.getAttempt(),
                        whoareyou.isCorrect(),
                        guessedList
                )
        );
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<WhoareyouSummitAnswerResponse> submitAnswer(@RequestBody WhoareyouSummitAnswerRequest request) {
        WhoareyouSummitAnswerResponse response = wheareyouService.submitAnswer(
                request.getInput(),
                request.getAttempts(),
                request.getAnswer(),
                request.getGuessedList()
        );
        return ResponseEntity.ok(response);
    }

    /*
    @PostMapping("/start")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> startQuiz() {

        log.info("Set Quiz...");
        WhoareyouResponse answer = wheAreYouService.getRandomProgamer();
        String imagePath = wheAreYouService.getImagePath(answer);
        log.info("Finish Set Quiz...");
        log.info("Answer : " + answer.getId() + " / " + answer.getProgamerTag() + " / " + answer.getName());

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
        WhoareyouResponse answer = jacksonObjectMapper.convertValue(payload.get("answer"), WhoareyouResponse.class);
        List<WhoareyouResponse> guessedList = ((List<?>) payload.get("guessedList")).stream()
                .map(item -> jacksonObjectMapper.convertValue(item, WhoareyouResponse.class))
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, Object> response = new HashMap<>();
        Optional<ProgamerInsertResponse> submitProgamer = wheAreYouService.findByPid(userInput);
        if (submitProgamer.isPresent()) {
            WhoareyouResponse curProgamer = WhoareyouResponse.of(submitProgamer);
            log.info("Submit Progamer: " + curProgamer.getId() + " / " + curProgamer.getProgamerTag());
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
                } else {
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
    }*/
}
