package pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;

import java.util.List;

@Getter
public class ValorantWhichIsTeamQuizResponse {

    private final Long id;
    private final Long index;
    private final String teamName;
    private final String teamCallName;
    private final Long seasonYear;
    private final String league;
    private final List<ProgamerValorantSimpleInfoResponse> rosters;

    private ValorantWhichIsTeamQuizResponse(Long id, Long index, String teamName, String teamCallName,Long seasonYear, String league, List<ProgamerValorantSimpleInfoResponse> rosters) {
        this.id = id;
        this.index = index;
        this.teamName = teamName;
        this.teamCallName = teamCallName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.rosters = rosters;
    }

    public static ValorantWhichIsTeamQuizResponse of(Long id, Long index, String teamName, String teamCallName,Long seasonYear, String league , List<ProgamerValorantSimpleInfoResponse> rosters) {
        return new ValorantWhichIsTeamQuizResponse(id, index, teamName, teamCallName, seasonYear, league, rosters);
    }

}
