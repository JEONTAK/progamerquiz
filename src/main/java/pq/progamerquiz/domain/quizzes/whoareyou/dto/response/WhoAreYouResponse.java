package pq.progamerquiz.domain.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;

import java.util.List;

@Getter
public class WhoAreYouResponse {

    private final Long id;
    private final ProgamerWithRecentTeamResponse answer;
    private final Long attempts;
    private final boolean correct;
    private final List<String> guessedList;

    private WhoAreYouResponse(Long id, ProgamerWithRecentTeamResponse answer, Long attempts, boolean correct, List<String> guessedList) {
        this.id = id;
        this.answer = answer;
        this.attempts = attempts;
        this.correct = correct;
        this.guessedList = guessedList;
    }

    public static WhoAreYouResponse of(Long id, ProgamerWithRecentTeamResponse progamerResponse, Long attempt, boolean correct, List<String> guessedList) {
        return new WhoAreYouResponse(id, progamerResponse, attempt, correct, guessedList);
    }

}
