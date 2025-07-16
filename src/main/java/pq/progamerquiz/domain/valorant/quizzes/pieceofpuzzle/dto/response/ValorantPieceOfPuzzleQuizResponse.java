package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;

import java.util.List;

@Getter
public class ValorantPieceOfPuzzleQuizResponse {

    private final Long id;
    private final Long index;
    private final String teamName;
    private final String teamCallName;
    private final Long seasonYear;
    private final Long progamerId;
    private final String progamerTag;
    private final String nationality;
    private final List<ProgamerValorantSimpleInfoResponse> rosters;

    private ValorantPieceOfPuzzleQuizResponse(Long id, Long index, String teamName, String teamCallName, Long seasonYear, Long progamerId, String progamerTag, String nationality, List<ProgamerValorantSimpleInfoResponse> rosters) {
        this.id = id;
        this.index = index;
        this.teamName = teamName;
        this.teamCallName = teamCallName;
        this.seasonYear = seasonYear;
        this.progamerId = progamerId;
        this.progamerTag = progamerTag;
        this.nationality = nationality;
        this.rosters = rosters;
    }

    public static ValorantPieceOfPuzzleQuizResponse of(Long id, Long index, String teamName, String teamCallName, Long seasonYear, Long progamerId, String progamerTag, String nationality, List<ProgamerValorantSimpleInfoResponse> rosters) {
        return new ValorantPieceOfPuzzleQuizResponse(id, index, teamName,teamCallName, seasonYear, progamerId, progamerTag, nationality, rosters);
    }

}
