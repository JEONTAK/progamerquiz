package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response;


import lombok.Getter;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantSimpleInfoResponse;

import java.util.List;

@Getter
public class ValorantIGotYouQuizResponse {

    private final Long id;
    private final Long index;
    private final Long progamerId;
    private final String progamerTag;
    private final String nationality;
    private final List<TeamValorantSimpleInfoResponse> teams;

    private ValorantIGotYouQuizResponse(Long id, Long index, Long progamerId, String progamerTag, String nationality, List<TeamValorantSimpleInfoResponse> teams) {
        this.id = id;
        this.index = index;
        this.progamerId = progamerId;
        this.progamerTag = progamerTag;
        this.nationality = nationality;
        this.teams = teams;
    }

    public static ValorantIGotYouQuizResponse of(Long id, Long idx, Long progamerId, String progamerTag, String nationality, List<TeamValorantSimpleInfoResponse> teams) {
        return new ValorantIGotYouQuizResponse(id, idx, progamerId, progamerTag, nationality, teams);
    }
}
