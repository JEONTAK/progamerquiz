package pq.progamerquiz.domain.valorant.teamvalorant.dto.response;

import lombok.Getter;

@Getter
public class TeamValorantInfoResponse {

    private final Long id;
    private final String name;
    private final String callName;
    private final String league;
    private final Long seasonYear;
    private final String seasonName;
    private final String seasonRank;

    private TeamValorantInfoResponse(Long id, String name, String callName, String league, Long seasonYear, String seasonName, String seasonRank) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.league = league;
        this.seasonYear = seasonYear;
        this.seasonName = seasonName;
        this.seasonRank = seasonRank;
    }

    public static TeamValorantInfoResponse of(Long id, String name, String callName, String league, Long seasonYear, String seasonName, String seasonRank) {
        return new TeamValorantInfoResponse(id, name, callName, league, seasonYear, seasonName, seasonRank);
    }

}
