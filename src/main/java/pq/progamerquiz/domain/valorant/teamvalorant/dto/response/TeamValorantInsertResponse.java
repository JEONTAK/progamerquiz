package pq.progamerquiz.domain.valorant.teamvalorant.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

@Getter
@NoArgsConstructor(force = true)
public class TeamValorantInsertResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final Long seasonYear;
    private final String league;
    private final String seasonName;
    private final String rank;

    private TeamValorantInsertResponse(Long id, String name, String callName, Long seasonYear, String league, String seasonName, String rank) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.seasonName = seasonName;
        this.rank = rank;
    }

    public static TeamValorantInsertResponse create(TeamValorant teamValorant) {
        return new TeamValorantInsertResponse(
                teamValorant.getId(), teamValorant.getName(), teamValorant.getCallName(), teamValorant.getSeasonYear(),
                teamValorant.getLeague(), teamValorant.getSeasonName(), teamValorant.getSeasonRank()
        );
    }
}
