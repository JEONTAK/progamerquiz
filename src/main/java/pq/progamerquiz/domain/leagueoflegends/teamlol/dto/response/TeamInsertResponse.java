package pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

@Getter
@NoArgsConstructor(force = true)
public class TeamInsertResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final Long seasonYear;
    private final String league;
    private final Long springRank;
    private final Long summerRank;
    private final Long msiRank;
    private final Long worldsRank;
    private final Long winterRank;
    private final Long imageId;

    private TeamInsertResponse(Long id, String name, String callName, Long seasonYear, String league, Long springRank, Long summerRank, Long msiRank, Long worldsRank, Long winterRank, Long imageId) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.springRank = springRank;
        this.summerRank = summerRank;
        this.msiRank = msiRank;
        this.worldsRank = worldsRank;
        this.winterRank = winterRank;
        this.imageId = imageId;
    }

    public static TeamInsertResponse create(TeamLOL teamLOL) {
        return new TeamInsertResponse(
                teamLOL.getId(), teamLOL.getName(), teamLOL.getCallName(), teamLOL.getSeasonYear(),
                teamLOL.getLeague(), teamLOL.getSpringRank(), teamLOL.getSummerRank(),
                teamLOL.getMsiRank(), teamLOL.getWorldsRank(), teamLOL.getWinterRank(), teamLOL.getImageId()
        );
    }
}
