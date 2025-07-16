package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLSimpleInfoResponse;

import java.util.List;

@Getter
public class LOLWhichIsTeamQuizResponse {

    private final Long id;
    private final Long index;
    private final String teamName;
    private final Long seasonYear;
    private final String league;
    private final Long imageId;
    private final List<ProgamerLOLSimpleInfoResponse> rosters;

    private LOLWhichIsTeamQuizResponse(Long id, Long index, String teamName, Long seasonYear, String league, Long imageId, List<ProgamerLOLSimpleInfoResponse> rosters) {
        this.id = id;
        this.index = index;
        this.teamName = teamName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.imageId = imageId;
        this.rosters = rosters;
    }

    public static LOLWhichIsTeamQuizResponse of(Long id, Long index, String teamName, Long seasonYear, String league, Long imageId, List<ProgamerLOLSimpleInfoResponse> rosters) {
        return new LOLWhichIsTeamQuizResponse(id, index, teamName, seasonYear, league, imageId, rosters);
    }

}
