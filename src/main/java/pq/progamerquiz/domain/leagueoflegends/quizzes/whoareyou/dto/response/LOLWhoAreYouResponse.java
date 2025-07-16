package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLWithRecentTeamResponse;

import java.util.List;

@Getter
public class LOLWhoAreYouResponse {

    private final Long id;
    private final ProgamerLOLWithRecentTeamResponse answer;
    private final Long attempts;
    private final boolean correct;
    private final List<String> guessedList;

    private LOLWhoAreYouResponse(Long id, ProgamerLOLWithRecentTeamResponse answer, Long attempts, boolean correct, List<String> guessedList) {
        this.id = id;
        this.answer = answer;
        this.attempts = attempts;
        this.correct = correct;
        this.guessedList = guessedList;
    }

    public static LOLWhoAreYouResponse of(Long id, ProgamerLOLWithRecentTeamResponse progamerResponse, Long attempt, boolean correct, List<String> guessedList) {
        return new LOLWhoAreYouResponse(id, progamerResponse, attempt, correct, guessedList);
    }

}
