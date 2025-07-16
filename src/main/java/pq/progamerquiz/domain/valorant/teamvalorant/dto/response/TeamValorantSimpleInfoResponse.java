package pq.progamerquiz.domain.valorant.teamvalorant.dto.response;

import lombok.Getter;

@Getter
public class TeamValorantSimpleInfoResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final String seasonName;
    private final Long seasonYear;

    private TeamValorantSimpleInfoResponse(Long id, String name, String callName, String seasonName, Long seasonYear) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonName = seasonName;
        this.seasonYear = seasonYear;
    }

    public static TeamValorantSimpleInfoResponse of(Long id, String name, String callName, String seasonName, Long seasonYear) {
        return new TeamValorantSimpleInfoResponse(id, name, callName, seasonName, seasonYear);
    }
}
