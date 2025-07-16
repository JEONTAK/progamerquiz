package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response;


import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamLOLSimpleInfoResponse;

import java.util.List;

@Getter
public class LOLIGotYouQuizResponse {

    private final Long id;
    private final Long index;
    private final Long progamerId;
    private final String progamerTag;
    private final Position position;
    private final List<TeamLOLSimpleInfoResponse> teams;

    private LOLIGotYouQuizResponse(Long id, Long index, Long progamerId, String progamerTag, Position position, List<TeamLOLSimpleInfoResponse> teams) {
        this.id = id;
        this.index = index;
        this.progamerId = progamerId;
        this.progamerTag = progamerTag;
        this.position = position;
        this.teams = teams;
    }

    public static LOLIGotYouQuizResponse of(Long id, Long idx, Long progamerId, String progamerTag, Position position, List<TeamLOLSimpleInfoResponse> teams) {
        return new LOLIGotYouQuizResponse(id, idx, progamerId, progamerTag, position, teams);
    }
}
