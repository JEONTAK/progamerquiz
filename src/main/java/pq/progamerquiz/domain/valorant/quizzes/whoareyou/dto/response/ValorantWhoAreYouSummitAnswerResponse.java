package pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantWithRecentTeamResponse;

import java.util.List;

@Getter
public class ValorantWhoAreYouSummitAnswerResponse {

    private final boolean isCorrect;
    private final Integer attempts;
    private final ProgamerValorantWithRecentTeamResponse answer;
    private final List<ValorantHintResult> hintResults;
    private final List<String> guessedList;

    private ValorantWhoAreYouSummitAnswerResponse(boolean isCorrect, Integer attempts, ProgamerValorantWithRecentTeamResponse answer, List<ValorantHintResult> hintResults, List<String> guessedList) {
        this.isCorrect = isCorrect;
        this.attempts = attempts;
        this.answer = answer;
        this.hintResults = hintResults;
        this.guessedList = guessedList;
    }

    public static ValorantWhoAreYouSummitAnswerResponse of(boolean isCorrect, Integer attempts, ProgamerValorantWithRecentTeamResponse answer, List<ValorantHintResult> hintResults, List<String> guessedList) {
        return new ValorantWhoAreYouSummitAnswerResponse(isCorrect, attempts, answer, hintResults, guessedList);
    }
}