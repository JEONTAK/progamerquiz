package pq.progamerquiz.domain.valorant.quizzes.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantWithRecentTeamResponse;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.service.ProgamerValorantQueryService;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response.ValorantHintResult;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response.ValorantWhoAreYouResponse;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response.ValorantWhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.entity.ValorantWhoAreYou;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.repository.ValorantWhoAreYouRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantInfoResponse;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.service.TeamValorantQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Quiz : Who are you?
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ValorantWheAreYouService {

    private final ProgamerValorantQueryService progamerValorantQueryService;
    private final ValorantWhoAreYouRepository valorantWhoAreYouRepository;
    private final TeamValorantQueryService teamValorantQueryService;
    private static final int MAX_ATTEMPTS = 8;

    public ValorantWhoAreYouResponse startQuiz() {
        ProgamerValorant randomProgamer = progamerValorantQueryService.findRandomProgamer();
        ValorantWhoAreYou whoareyou = ValorantWhoAreYou.create(0L, false, randomProgamer);
        whoareyou = valorantWhoAreYouRepository.save(whoareyou);
        ValorantWhoAreYou savedWhoAreYou = valorantWhoAreYouRepository.findByIdWIthProgamer(whoareyou.getId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        ProgamerValorant progamer = savedWhoAreYou.getQuizProgamerValorant();
        TeamValorant recentTeam = teamValorantQueryService.findRecentTeamByProgamer(progamer.getId());
        TeamValorantInfoResponse teamInfoResponse = TeamValorantInfoResponse.of(
                recentTeam.getId(),
                recentTeam.getName(),
                recentTeam.getCallName(),
                recentTeam.getLeague(),
                recentTeam.getSeasonYear(),
                recentTeam.getSeasonName(),
                recentTeam.getSeasonRank()
        );
        List<String> guessedList = new ArrayList<>();
        log.info(progamer.getProgamerTag());
        return ValorantWhoAreYouResponse.of(
                whoareyou.getId(),
                ProgamerValorantWithRecentTeamResponse.of(
                        progamer.getId(),
                        progamer.getProgamerTag(),
                        progamer.getName(),
                        progamer.getBirth(),
                        progamer.getLeagueWin(),
                        progamer.getMastersWin(),
                        progamer.getChampionsWin(),
                        progamer.getNationality(),
                        teamInfoResponse
                ),
                whoareyou.getAttempt(),
                whoareyou.isCorrect(),
                guessedList
        );
    }

    public ValorantWhoAreYouSummitAnswerResponse submitAnswer(String input, Integer attempts, ProgamerValorantWithRecentTeamResponse answer, List<String> guessedList) {
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
        List<ValorantHintResult> hintResults = compareHints(input, answer.getProgamerTag());

        // 응답 생성
        return ValorantWhoAreYouSummitAnswerResponse.of(isCorrect, attempts, answer, hintResults, guessedList);
    }

    private List<ValorantHintResult> compareHints(String input, String answer) {
        List<ValorantHintResult> results = new ArrayList<>();

        ProgamerValorant inputProgamer = progamerValorantQueryService.findByProgamerTag(input);
        ProgamerValorant answerProgamer = progamerValorantQueryService.findByProgamerTag(answer);
        TeamValorant inputRecentTeam = teamValorantQueryService.findRecentTeamByProgamerTag(input);
        TeamValorant answerRecentTeam = teamValorantQueryService.findRecentTeamByProgamerTag(answer);

        // 힌트 목록 (7가지 힌트)
        String[] hintNames = {"league", "team", "birth", "leagueWin", "mastersWin", "championsWin", "nationality"};

        Arrays.stream(hintNames).forEach(hintName -> {
            String inputValue = getHintValue(inputProgamer, inputRecentTeam, hintName);
            String answerValue = getHintValue(answerProgamer, answerRecentTeam, hintName);
            ValorantHintResult result = ValorantHintResult.of(hintName, inputValue, answerValue, inputValue.equalsIgnoreCase(answerValue));
            results.add(result);
        });

        return results;
    }

    private String getHintValue(ProgamerValorant progamer, TeamValorant team, String hintName) {
        // 실제 구현은 데이터 구조에 따라 다름
        // 리그, 최근팀, 포지션, 생년, 리그 우승, 국제 우승
        switch (hintName) {
            case "league":
                return team.getLeague();
            case "team":
                return team.getName() + ',' + team.getCallName();
            case "birth":
                return progamer.getBirth();
            case "leagueWin":
                return String.valueOf(progamer.getLeagueWin());
            case "mastersWin":
                return String.valueOf(progamer.getMastersWin());
            case "championsWin":
                return String.valueOf(progamer.getChampionsWin());
            case "nationality":
                return progamer.getNationality();
            default:
                return "Unknown";
        }
    }

    @Transactional
    public void saveResult(Long id, Long attempts, boolean isCorrect) {
        ValorantWhoAreYou currentQuiz = valorantWhoAreYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈 데이터를 찾을 수 없습니다."));
        currentQuiz.updateResult(attempts, isCorrect);
    }
}
