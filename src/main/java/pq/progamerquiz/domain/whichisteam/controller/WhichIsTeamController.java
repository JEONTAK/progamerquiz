package pq.progamerquiz.domain.whichisteam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.domain.whichisteam.dto.response.WhichIsTeamResponse;
import pq.progamerquiz.domain.team.dto.TeamDto;
import pq.progamerquiz.domain.whichisteam.service.WhichIsTeamService;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/whichisteam")
public class WhichIsTeamController {

    @Autowired
    private WhichIsTeamService whichIsTeamService;
    private List<WhichIsTeamResponse> quizList = new ArrayList<>();
    private String isSubmitted;
    private String isCorrect;
    private final int totalIndex = 15;
    private int correctCount;
    private int currentIndex;

    private void initialize() {
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
    }
}