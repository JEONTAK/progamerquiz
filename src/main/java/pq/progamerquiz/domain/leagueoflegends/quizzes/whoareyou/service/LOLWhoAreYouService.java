package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLWithRecentTeamResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.service.ProgamerLOLQueryService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.LOLHintResult;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.LOLWhoAreYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response.LOLWhoAreYouSummitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.entity.LOLWhoAreYou;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.repository.LOLWhoAreYouRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamLOLInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.service.TeamLOLQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Quiz : Who are you?
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LOLWhoAreYouService {

    private final ProgamerLOLQueryService progamerLOLQueryService;
    private final LOLWhoAreYouRepository lolWhoAreYouRepository;
    private final TeamLOLQueryService teamLOLQueryService;
    private static final int MAX_ATTEMPTS = 8;

    public LOLWhoAreYouResponse startQuiz() {
        ProgamerLOL randomProgamerLOL = progamerLOLQueryService.findRandomProgamer();
        LOLWhoAreYou whoareyou = LOLWhoAreYou.create(0L, false, randomProgamerLOL);
        whoareyou = lolWhoAreYouRepository.save(whoareyou);
        LOLWhoAreYou savedLOLWhoAreYou = lolWhoAreYouRepository.findByIdWIthProgamer(whoareyou.getId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        ProgamerLOL progamerLOL = savedLOLWhoAreYou.getQuizProgamerLOL();
        TeamLOL recentTeamLOL = teamLOLQueryService.findRecentTeamByProgamer(progamerLOL.getId());
        TeamLOLInfoResponse teamLOLInfoResponse = TeamLOLInfoResponse.of(
                recentTeamLOL.getId(),
                recentTeamLOL.getName(),
                recentTeamLOL.getCallName(),
                recentTeamLOL.getLeague(),
                recentTeamLOL.getSeasonYear(),
                recentTeamLOL.getSpringRank(),
                recentTeamLOL.getSummerRank(),
                recentTeamLOL.getMsiRank(),
                recentTeamLOL.getWorldsRank(),
                recentTeamLOL.getWinterRank(),
                recentTeamLOL.getImageId()
        );
        List<String> guessedList = new ArrayList<>();
        log.info(progamerLOL.getProgamerTag());
        return LOLWhoAreYouResponse.of(
                        whoareyou.getId(),
                        ProgamerLOLWithRecentTeamResponse.of(
                                progamerLOL.getId(),
                                progamerLOL.getProgamerTag(),
                                progamerLOL.getName(),
                                progamerLOL.getBirth(),
                                progamerLOL.getPosition(),
                                progamerLOL.getLeagueWin(),
                                progamerLOL.getIntlWin(),
                                progamerLOL.getNationality(),
                                teamLOLInfoResponse
                        ),
                        whoareyou.getAttempt(),
                        whoareyou.isCorrect(),
                        guessedList
                );
    }

    public LOLWhoAreYouSummitAnswerResponse submitAnswer(String input, Integer attempts, ProgamerLOLWithRecentTeamResponse answer, List<String> guessedList) {
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
        List<LOLHintResult> LOLHintResults = compareHints(input, answer.getProgamerTag());

        // 응답 생성
        return LOLWhoAreYouSummitAnswerResponse.of(isCorrect, attempts, answer, LOLHintResults, guessedList);
    }

    private List<LOLHintResult> compareHints(String input, String answer) {
        List<LOLHintResult> results = new ArrayList<>();

        ProgamerLOL inputProgamerLOL = progamerLOLQueryService.findByProgamerTag(input);
        ProgamerLOL answerProgamerLOL = progamerLOLQueryService.findByProgamerTag(answer);
        TeamLOL inputRecentTeamLOL = teamLOLQueryService.findRecentTeamByProgamerTag(input);
        TeamLOL answerRecentTeamLOL = teamLOLQueryService.findRecentTeamByProgamerTag(answer);

        // 힌트 목록 (예: 6가지 힌트)
        String[] hintNames = {"league", "team", "position", "birth", "leagueWin", "intlWin"};

        Arrays.stream(hintNames).forEach(hintName -> {
            String inputValue = getHintValue(inputProgamerLOL, inputRecentTeamLOL, hintName);
            String answerValue = getHintValue(answerProgamerLOL, answerRecentTeamLOL, hintName);
            LOLHintResult result = LOLHintResult.of(hintName, inputValue, answerValue, inputValue.equalsIgnoreCase(answerValue));
            results.add(result);
        });

        return results;
    }

    private String getHintValue(ProgamerLOL progamerLOL, TeamLOL teamLOL, String hintName) {
        // 실제 구현은 데이터 구조에 따라 다름
        // 리그, 최근팀, 포지션, 생년, 리그 우승, 국제 우승
        switch (hintName) {
            case "league":
                return teamLOL.getLeague();
            case "team":
                return teamLOL.getName() + "," + teamLOL.getImageId();
            case "teamImageId":
                return String.valueOf(teamLOL.getImageId());
            case "position":
                return String.valueOf(progamerLOL.getPosition());
            case "birth":
                return progamerLOL.getBirth();
            case "leagueWin":
                return String.valueOf(progamerLOL.getLeagueWin());
            case "intlWin":
                return String.valueOf(progamerLOL.getIntlWin());
            default:
                return "Unknown";
        }
    }

    @Transactional
    public void saveResult(Long id, Long attempts, boolean isCorrect) {
        LOLWhoAreYou currentQuiz = lolWhoAreYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈 데이터를 찾을 수 없습니다."));
        currentQuiz.updateResult(attempts, isCorrect);
    }
}
