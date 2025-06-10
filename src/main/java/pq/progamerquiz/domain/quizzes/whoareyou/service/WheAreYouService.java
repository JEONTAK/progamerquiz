package pq.progamerquiz.domain.quizzes.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.service.ProgamerQueryService;
import pq.progamerquiz.domain.quizzes.whoareyou.dto.response.HintResult;
import pq.progamerquiz.domain.quizzes.whoareyou.dto.response.WhoAreYouResponse;
import pq.progamerquiz.domain.quizzes.whoareyou.dto.response.WhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.quizzes.whoareyou.entity.WhoAreYou;
import pq.progamerquiz.domain.quizzes.whoareyou.repository.WhoAreYouRepository;
import pq.progamerquiz.domain.team.dto.response.TeamInfoResponse;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.service.TeamQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Quiz : Who are you?
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WheAreYouService {

    private final ProgamerQueryService progamerQueryService;
    private final WhoAreYouRepository whoareyouRepository;
    private final TeamQueryService teamQueryService;
    private static final int MAX_ATTEMPTS = 8;

    public WhoAreYouResponse startQuiz() {
        Progamer randomProgamer = progamerQueryService.findRandomProgamer();
        WhoAreYou whoareyou = WhoAreYou.create(0L, false, randomProgamer);
        whoareyou = whoareyouRepository.save(whoareyou);
        WhoAreYou savedWhoAreYou = whoareyouRepository.findByIdWIthProgamer(whoareyou.getId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        Progamer progamer = savedWhoAreYou.getQuizProgamer();
        Team recentTeam = teamQueryService.findRecentTeamByProgamer(progamer.getId());
        TeamInfoResponse teamInfoResponse = TeamInfoResponse.of(
                recentTeam.getId(),
                recentTeam.getName(),
                recentTeam.getCallName(),
                recentTeam.getLeague(),
                recentTeam.getSeasonYear(),
                recentTeam.getSpringRank(),
                recentTeam.getSummerRank(),
                recentTeam.getMsiRank(),
                recentTeam.getWorldsRank(),
                recentTeam.getWinterRank(),
                recentTeam.getImageId()
        );
        List<String> guessedList = new ArrayList<>();
        log.info(progamer.getProgamerTag());
        return WhoAreYouResponse.of(
                        whoareyou.getId(),
                        ProgamerWithRecentTeamResponse.of(
                                progamer.getId(),
                                progamer.getProgamerTag(),
                                progamer.getName(),
                                progamer.getBirth(),
                                progamer.getPosition(),
                                progamer.getLeagueWin(),
                                progamer.getIntlWin(),
                                progamer.getNationality(),
                                teamInfoResponse
                        ),
                        whoareyou.getAttempt(),
                        whoareyou.isCorrect(),
                        guessedList
                );
    }

    public WhoAreYouSummitAnswerResponse submitAnswer(String input, Integer attempts, ProgamerWithRecentTeamResponse answer, List<String> guessedList) {
        // 시도 횟수 증가
        attempts++;

        // 정답 확인
        boolean isCorrect = input.trim().equalsIgnoreCase(answer.getProgamerTag());

        if (attempts >= MAX_ATTEMPTS) {
            input = answer.getProgamerTag();
        }

        // 추측 목록 업데이트
        if (!isCorrect && !guessedList.contains(input)) {
            guessedList.add(input);
        }

        // 힌트 비교
        List<HintResult> hintResults = compareHints(input, answer.getProgamerTag());

        // 응답 생성
        return WhoAreYouSummitAnswerResponse.of(isCorrect, attempts, answer, hintResults, guessedList);
    }

    private List<HintResult> compareHints(String input, String answer) {
        List<HintResult> results = new ArrayList<>();

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
        WhoAreYou currentQuiz = whoareyouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈 데이터를 찾을 수 없습니다."));
        currentQuiz.updateResult(attempts, isCorrect);
    }
}
