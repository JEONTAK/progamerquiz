package pq.progamerquiz.domain.team.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.team.entity.Team;

@Getter
@NoArgsConstructor
public class TeamInsertResponse {

    private Long id;
    private String name;
    private String callName;
    private Long seasonYear;
    private String league;
    private Long springRank;
    private Long summerRank;
    private Long msiRank;
    private Long worldsRank;
    private Long winterRank;
    private Long imageId;

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

    public static TeamInsertResponse create(Team team) {
        return new TeamInsertResponse(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague(), team.getSpringRank(), team.getSummerRank(),
                team.getMsiRank(), team.getWorldsRank(), team.getWinterRank(), team.getImageId()
        );
    }
}
