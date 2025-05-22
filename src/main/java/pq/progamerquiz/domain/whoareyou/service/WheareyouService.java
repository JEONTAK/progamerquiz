package pq.progamerquiz.domain.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.service.ProgamerCommandService;
import pq.progamerquiz.domain.progamer.service.ProgamerQueryService;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.service.TeamQueryService;
import pq.progamerquiz.domain.whoareyou.dto.response.HintResult;
import pq.progamerquiz.domain.whoareyou.dto.response.WhoareyouSummitAnswerResponse;
import pq.progamerquiz.domain.whoareyou.entity.Whoareyou;
import pq.progamerquiz.domain.whoareyou.repository.WhoareyouRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Quiz : Who are you?
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WheareyouService {

    final private ProgamerCommandService progamerCommandService;
    private final ProgamerQueryService progamerQueryService;
    private final WhoareyouRepository whoareyouRepository;
    private static final int MAX_ATTEMPTS = 8;
    private final TeamQueryService teamQueryService;

    public Whoareyou startQuiz() {
        Progamer randomProgamer = progamerQueryService.findRandomProgamer();
        Whoareyou whoareyou = Whoareyou.create(0L, false, randomProgamer);
        whoareyou = whoareyouRepository.save(whoareyou);
        return whoareyou;
    }

    public WhoareyouSummitAnswerResponse submitAnswer(String input, Integer attempts, ProgamerWithRecentTeamResponse answer, List<String> guessedList) {
        log.info("Input : " + input +  " | Answer : " + answer.getProgamerTag(), " | Attempt : " + attempts);
        // 시도 횟수 증가
        attempts++;

        // 정답 확인
        boolean isCorrect = input.trim().equalsIgnoreCase(answer.getProgamerTag());

        if(attempts >= MAX_ATTEMPTS) {
            input = answer.getProgamerTag();
        }

        // 추측 목록 업데이트
        if (!isCorrect && !guessedList.contains(input)) {
            guessedList.add(input);
        }

        // 힌트 비교
        List<HintResult> hintResults = compareHints(input, answer.getProgamerTag());

        // 응답 생성
        return WhoareyouSummitAnswerResponse.of(isCorrect, attempts, answer, hintResults, guessedList);
    }

    private List<HintResult> compareHints(String input, String answer) {
        List<HintResult> results = new ArrayList<>();

        // 예: 프로게이머 데이터 조회 (실제로는 DB 또는 JSON에서 조회)
        Progamer inputProgamer = progamerQueryService.findByProgamerTag(input);
        Progamer answerProgamer = progamerQueryService.findByProgamerTag(answer);
        Team inputRecentTeam = teamQueryService.findRecentTeamByProgamerTag(input);
        Team answerRecentTeam = teamQueryService.findRecentTeamByProgamerTag(answer);

        // 힌트 목록 (예: 6가지 힌트)
        String[] hintNames = {"league", "team", "position", "birth", "leagueWin", "intlWin"};

        Arrays.stream(hintNames).forEach(hintName -> {
            String inputValue = getHintValue(inputProgamer, inputRecentTeam, hintName);
            String answerValue = getHintValue(answerProgamer, answerRecentTeam, hintName);
            HintResult result = HintResult.of(hintName, inputValue, answerValue, inputValue.equalsIgnoreCase(answerValue));
            results.add(result);
        });

        return results;
    }

    private String getHintValue(Progamer progamer, Team team, String hintName) {
        // 실제 구현은 데이터 구조에 따라 다름
        // 리그, 최근팀, 포지션, 생년, 리그 우승, 국제 우승
        switch (hintName) {
            case "league":
                return team.getLeague();
            case "team":
                return team.getName() + "," + team.getImageId();
            case "teamImageId":
                return String.valueOf(team.getImageId());
            case "position":
                return String.valueOf(progamer.getPosition());
            case "birth":
                return progamer.getBirth();
            case "leagueWin":
                return String.valueOf(progamer.getLeagueWin());
            case "intlWin":
                return String.valueOf(progamer.getIntlWin());
            default:
                return "Unknown";
        }
    }

    @Transactional
    public void saveResult(Long id, Long attempts, boolean isCorrect) {
        log.info(id + " "  + attempts + " " + isCorrect);
        Whoareyou currentQuiz = whoareyouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈 데이터를 찾을 수 없습니다."));
        currentQuiz.updateResult(attempts, isCorrect);
    }
}
