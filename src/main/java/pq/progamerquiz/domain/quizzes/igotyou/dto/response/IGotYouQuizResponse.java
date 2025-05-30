package pq.progamerquiz.domain.quizzes.igotyou.dto.response;


import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;

import java.util.List;

//Quiz : I Got you!
@Getter
public class IGotYouQuizResponse {

    private final Long id;
    private final Long index;
    private final Long progamerId;
    private final String progamerTag;
    private final Position position;
    private final List<TeamSimpleInfoResponse> teams;

    private IGotYouQuizResponse(Long id, Long index, Long progamerId, String progamerTag, Position position, List<TeamSimpleInfoResponse> teams) {
        this.id = id;
        this.index = index;
        this.progamerId = progamerId;
        this.progamerTag = progamerTag;
        this.position = position;
        this.teams = teams;
    }

    public static IGotYouQuizResponse of(Long id, Long idx, Long progamerId, String progamerTag, Position position, List<TeamSimpleInfoResponse> teams) {
        return new IGotYouQuizResponse(id, idx, progamerId, progamerTag, position, teams);
    }
}
