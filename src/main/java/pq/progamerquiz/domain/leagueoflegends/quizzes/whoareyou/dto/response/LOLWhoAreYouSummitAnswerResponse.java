package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLWithRecentTeamResponse;

import java.util.List;

@Getter
public class LOLWhoAreYouSummitAnswerResponse {

    private final boolean isCorrect;
    private final Integer attempts;
    private final ProgamerLOLWithRecentTeamResponse answer;
    private final List<LOLHintResult> hintResults;
    private final List<String> guessedList;

    private LOLWhoAreYouSummitAnswerResponse(boolean isCorrect, Integer attempts, ProgamerLOLWithRecentTeamResponse answer, List<LOLHintResult> hintResults, List<String> guessedList) {
        this.isCorrect = isCorrect;
        this.attempts = attempts;
        this.answer = answer;
        this.hintResults = hintResults;
        this.guessedList = guessedList;
    }

    public static LOLWhoAreYouSummitAnswerResponse of(boolean isCorrect, Integer attempts, ProgamerLOLWithRecentTeamResponse answer, List<LOLHintResult> hintResults, List<String> guessedList) {
        return new LOLWhoAreYouSummitAnswerResponse(isCorrect, attempts, answer, hintResults, guessedList);
    }
}