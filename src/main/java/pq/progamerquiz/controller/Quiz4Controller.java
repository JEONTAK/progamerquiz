package pq.progamerquiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pq.progamerquiz.service.ProgamerService;
import pq.progamerquiz.dto.Quiz4Dto;
import pq.progamerquiz.service.Quiz4Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/pieceofpuzzle")
@Transactional
public class Quiz4Controller {

    @Autowired
    private Quiz4Service quiz4Service;
    private List<Quiz4Dto> quizList;
    private String isSubmitted;
    private String isCorrect;
    private String isFinish;
    private final int totalIndex = 15;
    private final int attemptsPerTeam = 3;
    private int correctCount;
    private int currentIndex;

    // 각 팀에서 맞춘 선수 카운트 (두 명 맞춰야 정답 처리)
    private Map<Integer, Integer> correctCountsForTeam = new HashMap<>();
    @Autowired
    private ProgamerService progamerService;


    private void initialize() {
        isSubmitted = "true";
        isCorrect = "start";
        isFinish = "false";
        correctCount = 0;
        currentIndex = 0;
        quizList = new ArrayList<>();
        log.info("Set Quiz...");
        quizList = quiz4Service.getTeams(totalIndex, "LCK");
        log.info("Finish Set Quiz..." + " " + quizList.size());
        for (Quiz4Dto quiz4Dto : quizList) {
            log.info("Index : " + quiz4Dto.getIndex() + " | Team : " + quiz4Dto.getTeamName() + " | Year : " + quiz4Dto.getTeamYear());
            for (Map<Long, Boolean> map : quiz4Dto.getAnswer()) {
                for (Map.Entry<Long, Boolean> answer : map.entrySet()) {
                    log.info("Answer : " + progamerService.findOne(answer.getKey()).getPid() + " : " + answer.getValue());
                }
            }
        }
        correctCountsForTeam.clear();
        for (int i = 0; i < quizList.size(); i++) {
            correctCountsForTeam.put(i, 0); // 각 팀의 맞춘 선수를 0으로 초기화
        }
    }

    // 퀴즈 시작
    @GetMapping
    public String startQuiz(Model model) {
        log.info("Piece of puzzle");
        initialize();
        return "redirect:/pieceofpuzzle/quiz";
    }

    // 퀴즈 로드
    @GetMapping("/quiz")
    public String loadQuiz(@RequestParam(value = "currentIndex", defaultValue = "0") Integer cIdx, Model model) {
        if (cIdx == null) {
            // currentIndex가 null일 경우 기본값 0으로 리디렉션
            return "redirect:/pieceofpuzzle/quiz?currentIndex=0";
        }
        currentIndex = cIdx;
        isSubmitted = "true";
        isCorrect = "start";
        isFinish = "false";
        model.addAttribute("quizList", quizList);
        model.addAttribute("isSubmitted", isSubmitted);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("currentIndex", cIdx);
        log.info("Current : " + currentIndex + " / "
                + quizList.get(currentIndex).toString());
        return "quizzes/pieceofpuzzle";
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
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> payload) {
        String input = (String) payload.get("input");
        Map<String, Object> response = new HashMap<>();

        // 유효성 검사
        if (input == null || input.isEmpty()) {
            response.put("message", "Input cannot be null or empty");
            return ResponseEntity.badRequest().body(response);
        }

        if ( currentIndex < 0 || currentIndex >= quizList.size()) {
            response.put("message", "Invalid currentIndex");
            return ResponseEntity.badRequest().body(response);
        }

        Quiz4Dto currentTeam = quizList.get(currentIndex);
        Long correctId = null;

        //존재
        if (quiz4Service.isExist(input)) {
            isSubmitted = "true";
            currentTeam.setAttempts(currentTeam.getAttempts() + 1);
            //정답
            if (quiz4Service.isAnswer(input, currentTeam)) {
                isCorrect = "true";
                currentTeam.setCorrect(currentTeam.getCorrect() + 1);
                correctId = progamerService.findByPid(input).getId();
            }
            //오답
            else {
                isCorrect = "false";
            }
        }
        //존재X
        else {
            isSubmitted = "false";
            isCorrect = "none";
        }

        if (currentTeam.getCorrect() == 2) {
            isFinish = "true";
            correctCount++;
        }else if(currentTeam.getAttempts() == attemptsPerTeam){
            isFinish = "true";
        }

        response.put("isFinish", isFinish);
        response.put("isSubmitted", isSubmitted);
        response.put("isCorrect", isCorrect);
        response.put("correctId", correctId);
        log.info("isFinish : " + isFinish
        + "isSubmitted : " + isSubmitted
        + "isCorrect : " + isCorrect
        + "correctId : " + correctId
        + "correctCount : " + correctCount
        + "currentIndex : " + currentIndex);
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