package pq.progamerquiz.domain.quizzes.whichisteam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.request.WhichIsTeamStartRequest;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamQuizResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.service.WhichIsTeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/whichisteam")
public class WhichIsTeamController {

    private final WhichIsTeamService whichIsTeamService;

    @GetMapping
    public String renderQuizPage() {
        log.info("Which Is Team?");
        return "quizzes/whichisteam";
    }

    @PostMapping("/select")
    @ResponseBody
    public ResponseEntity<WhichIsTeamResponse> setQuiz(@RequestBody WhichIsTeamStartRequest request) {
        log.info("Request size : " + request.getTotalQuizCount());
        log.info("Set Quiz...");
        List<WhichIsTeamQuizResponse> quizList = whichIsTeamService.setQuizLists(request.getTotalQuizCount());
        log.info("Finish Set Quiz...");
        for (WhichIsTeamQuizResponse whichIsTeamQuizResponse : quizList) {
            log.info(whichIsTeamQuizResponse.getIndex() + " : " + whichIsTeamQuizResponse.getTeamName());
            for (int i = 0; i < whichIsTeamQuizResponse.getRosters().size(); i++) {
                log.info("      Progamer : " + whichIsTeamQuizResponse.getRosters().get(i).getProgamerTag());
            }
        }
        WhichIsTeamResponse response = whichIsTeamService.setQuiz(quizList.get(0).getId(), quizList);
        return ResponseEntity.ok(response);
    }

    /*private void initialize() {
        isSubmitted = "true";
        isCorrect = "start";
        correctCount = 0;
        currentIndex = 0;
        quizList.clear();
        log.info("Set Quiz...");
        quizList = whichIsTeamService.getTeams(totalIndex, null);
        log.info("Finish Set Quiz...");
        for (WhichIsTeamResponse whichIsTeamResponse : quizList) {
            log.info("      Team : " + whichIsTeamResponse.getTeamId() + " / " + whichIsTeamResponse.getTeamName());
        }
    }

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Which is Team?");
        initialize();
        return "redirect:/whichisteam/quiz";
    }

    @GetMapping("/quiz")
    public String gettingQuiz(@RequestParam(value = "currentIndex", required = false) Integer cIdx, Model model) {
        if (cIdx == null) {
            // currentIndex가 null일 경우 기본값 0으로 리디렉션
            return "redirect:/whichisteam/quiz?currentIndex=0";
        }
        currentIndex = cIdx;
        model.addAttribute("quizList", quizList);
        model.addAttribute("isSubmitted", isSubmitted);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("totalIndex", totalIndex);
        model.addAttribute("currentIndex", cIdx);
        log.info("Current : " + currentIndex + " / "
                + quizList.get(currentIndex).getTeamName());
        return "quizzes/whichisteam";
    }

    @GetMapping("/quiz/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getQuiz(@RequestParam(value = "currentIndex", required = false) Integer currentIndex) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSubmitted", isSubmitted);
        result.put("isCorrect", isCorrect);
        result.put("currentIndex", currentIndex);
        result.put("currentTeam", quizList.get(currentIndex));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/submitAnswer")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, String> payload) {
        String userInput = payload.get("input");
        log.info("Submitting answer: " + userInput);
        Map<String, Object> response = new HashMap<>();
        List<TeamDto> submitTeam = whichIsTeamService.findByName(userInput);
        if (submitTeam.isEmpty()) {
            isSubmitted = "false";
            isCorrect = "none";
        } else {
            if (whichIsTeamService.isAnswer(submitTeam, quizList.get(currentIndex))) {
                isSubmitted = "true";
                isCorrect = "true";
                correctCount++;
            } else {
                isSubmitted = "true";
                isCorrect = "false";
            }
        }
        response.put("isSubmitted", isSubmitted);
        response.put("isCorrect", isCorrect);
        log.info("isSubmitted: " + isSubmitted
        + "\nisCorrect: " + isCorrect
        + "\ncurrentIndex: " + currentIndex);
        return ResponseEntity.ok(response);
    }

    // 퀴즈 끝난 후, 맞춘 개수와 전체 개수를 넘기는 /end 처리
    @GetMapping("/end")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> quizEnd() {
        log.info("Finish Quiz (I Got you!) Result : " + correctCount + " / " + totalIndex);
        Map<String, Object> result = new HashMap<>();
        result.put("correctCount", correctCount);
        result.put("totalCount", totalIndex);
        return new ResponseEntity<>(result, HttpStatus.OK); // 결과를 JSON 형태로 반환
    }*/
}