package pq.progamerquiz.domain.quizzes.pieceofpuzzle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.service.PieceOfPuzzleService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pieceofpuzzle")
public class PieceOfPuzzleController {

    private final PieceOfPuzzleService pieceOfPuzzleService;
/*    private List<PieceOfPuzzleResponse> quizList;
    private String isSubmitted;
    private String isCorrect;
    private String isFinish;
    private final int totalIndex = 15;
    private final int attemptsPerTeam = 3;
    private int correctCount;
    private int currentIndex;*/

   /* // 각 팀에서 맞춘 선수 카운트 (두 명 맞춰야 정답 처리)
    private Map<Integer, Integer> correctCountsForTeam = new HashMap<>();
    @Autowired
    private ProgamerService progamerService;
    @Autowired
    private TeamRepository teamRepository;


    private void initialize() {
        isSubmitted = "true";
        isCorrect = "start";
        isFinish = "false";
        correctCount = 0;
        currentIndex = 0;
        quizList = new ArrayList<>();
        log.info("Set Quiz...");
        quizList = pieceOfPuzzleService.getTeams(totalIndex, "LCK");
        log.info("Finish Set Quiz..." + " " + quizList.size());
        for (PieceOfPuzzleResponse pieceOfPuzzleResponse : quizList) {
            log.info("Index : " + pieceOfPuzzleResponse.getIndex() + " | Team : " + pieceOfPuzzleResponse.getTeamName() + " | Year : " + pieceOfPuzzleResponse.getTeamYear());
            for (Map<Long, Boolean> map : pieceOfPuzzleResponse.getAnswer()) {
                for (Map.Entry<Long, Boolean> answer : map.entrySet()) {
                    log.info("Answer : " + progamerService.findOne(answer.getKey()).getProgamerTag() + " : " + answer.getValue());
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
                + quizList.get(currentIndex).getTeamName() + " / " + quizList.get(currentIndex).getAnswer().stream().toList());
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
       *//* if (input == null || input.isEmpty()) {
            response.put("message", "Input cannot be null or empty");
            return ResponseEntity.badRequest().body(response);
        }*//*

        if ( currentIndex < 0 || currentIndex >= quizList.size()) {
            response.put("message", "Invalid currentIndex");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<ProgamerInsertResponse> submitProgamer = pieceOfPuzzleService.findByPid(input);
        PieceOfPuzzleResponse currentTeam = quizList.get(currentIndex);
        Long correctId = null;

        //존재
        if (submitProgamer.isPresent()) {
            isSubmitted = "true";
            currentTeam.updateAttempts();
            //정답
            if (pieceOfPuzzleService.isAnswer(submitProgamer, currentTeam)) {
                isCorrect = "true";
                currentTeam.updateCorrect();
                correctId = submitProgamer.get().getId();
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
        + "\nisSubmitted : " + isSubmitted
        + "\nisCorrect : " + isCorrect
        + "\ncorrectId : " + correctId
        + "\ncorrectCount : " + correctCount
        + "\ncurrentIndex : " + currentIndex);
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