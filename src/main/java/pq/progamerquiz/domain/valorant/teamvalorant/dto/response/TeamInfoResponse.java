package pq.progamerquiz.domain.valorant.teamvalorant.dto.response;

import lombok.Getter;

@Getter
public class TeamInfoResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final String league;
    private final Long seasonYear;
    private final Long springRank;
    private final Long summerRank;
    private final Long msiRank;
    private final Long worldsRank;
    private final Long winterRank;
    private final Long imageId;

    private TeamInfoResponse(Long id, String name, String callName, String league, Long seasonYear, Long springRank, Long summerRank, Long msiRank, Long worldsRank, Long winterRank, Long imageId) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.league = league;
        this.seasonYear = seasonYear;
        this.springRank = springRank;
        this.summerRank = summerRank;
        this.msiRank = msiRank;
        this.worldsRank = worldsRank;
        this.winterRank = winterRank;
        this.imageId = imageId;
    }

    public static TeamInfoResponse of(Long id, String name, String callName, String league, Long seasonYear, Long springRank, Long summerRank, Long msiRank, Long worldsRank, Long winterRank, Long imageId) {
        return new TeamInfoResponse(id, name, callName, league, seasonYear, springRank, summerRank, msiRank, worldsRank, winterRank, imageId);
    }

}
