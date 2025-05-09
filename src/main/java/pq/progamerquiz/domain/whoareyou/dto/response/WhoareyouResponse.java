package pq.progamerquiz.domain.whoareyou.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;

@Getter
public class WhoareyouResponse {

    private final Long id;
    private final ProgamerWithRecentTeamResponse progamerResponse;
    private final Long attempt;
    private final boolean correct;

    private WhoareyouResponse(Long id, ProgamerWithRecentTeamResponse progamerResponse, Long attempt, boolean correct) {
        this.id = id;
        this.progamerResponse = progamerResponse;
        this.attempt = attempt;
        this.correct = correct;
    }

    public static WhoareyouResponse of(Long id, ProgamerWithRecentTeamResponse progamerResponse, Long attempt, boolean correct) {
        return new WhoareyouResponse(id, progamerResponse, attempt, correct);
    }

}
