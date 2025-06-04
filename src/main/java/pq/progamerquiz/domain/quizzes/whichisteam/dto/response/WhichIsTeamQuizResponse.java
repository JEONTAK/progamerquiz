package pq.progamerquiz.domain.quizzes.whichisteam.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerSimpleInfoResponse;

import java.util.List;

@Getter
public class WhichIsTeamQuizResponse {

    private final Long id;
    private final Long index;
    private final String teamName;
    private final Long seasonYear;
    private final String league;
    private final Long imageId;
    private final List<ProgamerSimpleInfoResponse> rosters;

    private WhichIsTeamQuizResponse(Long id, Long index, String teamName, Long seasonYear, String league, Long imageId, List<ProgamerSimpleInfoResponse> rosters) {
        this.id = id;
        this.index = index;
        this.teamName = teamName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.imageId = imageId;
        this.rosters = rosters;
    }

    public static WhichIsTeamQuizResponse of(Long id, Long index, String teamName, Long seasonYear, String league, Long imageId, List<ProgamerSimpleInfoResponse> rosters) {
        return new WhichIsTeamQuizResponse(id, index, teamName, seasonYear, league, imageId, rosters);
    }

}
