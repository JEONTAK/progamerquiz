package pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantWithRecentTeamResponse;

import java.util.List;

@Getter
public class ValorantWhoAreYouResponse {

    private final Long id;
    private final ProgamerValorantWithRecentTeamResponse answer;
    private final Long attempts;
    private final boolean correct;
    private final List<String> guessedList;

    private ValorantWhoAreYouResponse(Long id, ProgamerValorantWithRecentTeamResponse answer, Long attempts, boolean correct, List<String> guessedList) {
        this.id = id;
        this.answer = answer;
        this.attempts = attempts;
        this.correct = correct;
        this.guessedList = guessedList;
    }

    public static ValorantWhoAreYouResponse of(Long id, ProgamerValorantWithRecentTeamResponse progamerResponse, Long attempt, boolean correct, List<String> guessedList) {
        return new ValorantWhoAreYouResponse(id, progamerResponse, attempt, correct, guessedList);
    }

}
