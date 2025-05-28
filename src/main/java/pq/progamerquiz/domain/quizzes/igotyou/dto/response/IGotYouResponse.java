package pq.progamerquiz.domain.quizzes.igotyou.dto.response;


import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;

import java.util.List;

//Quiz : I Got you!
@Getter
public class IGotYouResponse {

    private final Long index;
    private final Long id;
    private final String progamerTag;
    private final Position position;
    private final List<TeamSimpleInfoResponse> teams;

    private IGotYouResponse(Long index, Long id, String progamerTag, Position position, List<TeamSimpleInfoResponse> teams) {
        this.index = index;
        this.id = id;
        this.progamerTag = progamerTag;
        this.position = position;
        this.teams = teams;
    }

    public static IGotYouResponse of(Long idx, Long id, String progamerTag, Position position, List<TeamSimpleInfoResponse> teams) {
        return new IGotYouResponse(idx, id, progamerTag, position, teams);
    }
}
