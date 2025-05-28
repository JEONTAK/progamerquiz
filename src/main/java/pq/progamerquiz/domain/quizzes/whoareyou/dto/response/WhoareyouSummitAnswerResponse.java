package pq.progamerquiz.domain.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;

import java.util.List;

@Getter
public class WhoareyouSummitAnswerResponse {

    private final boolean isCorrect;
    private final Integer attempts;
    private final ProgamerWithRecentTeamResponse answer;
    private final List<HintResult> hintResults;
    private final List<String> guessedList;

    private WhoareyouSummitAnswerResponse(boolean isCorrect, Integer attempts, ProgamerWithRecentTeamResponse answer, List<HintResult> hintResults, List<String> guessedList) {
        this.isCorrect = isCorrect;
        this.attempts = attempts;
        this.answer = answer;
        this.hintResults = hintResults;
        this.guessedList = guessedList;
    }

    public static WhoareyouSummitAnswerResponse of(boolean isCorrect, Integer attempts, ProgamerWithRecentTeamResponse answer, List<HintResult> hintResults, List<String> guessedList) {
        return new WhoareyouSummitAnswerResponse(isCorrect, attempts, answer, hintResults, guessedList);
    }
}