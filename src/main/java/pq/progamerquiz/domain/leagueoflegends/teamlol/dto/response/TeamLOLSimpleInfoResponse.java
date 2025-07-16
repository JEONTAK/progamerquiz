package pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response;

import lombok.Getter;

@Getter
public class TeamLOLSimpleInfoResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final Long seasonYear;
    private final Long imageId;

    private TeamLOLSimpleInfoResponse(Long id, String name, String callName, Long seasonYear, Long imageId) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.imageId = imageId;
    }

    public static TeamLOLSimpleInfoResponse of(Long id, String name, String callName, Long seasonYear, Long imageId) {
        return new TeamLOLSimpleInfoResponse(id, name, callName, seasonYear, imageId);
    }
}
