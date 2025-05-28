package pq.progamerquiz.domain.team.dto.response;

import lombok.Getter;

@Getter
public class TeamSimpleInfoResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final Long seasonYear;
    private final Long imageId;

    private TeamSimpleInfoResponse(Long id, String name, String callName, Long seasonYear, Long imageId) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.imageId = imageId;
    }

    public static TeamSimpleInfoResponse of(Long id, String name, String callName, Long seasonYear, Long imageId) {
        return new TeamSimpleInfoResponse(id, name, callName, seasonYear, imageId);
    }
}
