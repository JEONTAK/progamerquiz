package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response;

import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLSimpleInfoResponse;

import java.util.List;

@Getter
public class LOLPieceOfPuzzleQuizResponse {

    private final Long id;
    private final Long index;
    private final String teamName;
    private final Long seasonYear;
    private final Long imageId;
    private final Long answerProgamerId;
    private final String answerProgamerTag;
    private final Position answerProgamerPosition;
    private final List<ProgamerLOLSimpleInfoResponse> rosters;

    private LOLPieceOfPuzzleQuizResponse(Long id, Long index, String teamName, Long seasonYear, Long imageId, Long answerProgamerId, String answerProgamerTag, Position answerProgamerPosition, List<ProgamerLOLSimpleInfoResponse> rosters) {
        this.id = id;
        this.index = index;
        this.teamName = teamName;
        this.seasonYear = seasonYear;
        this.imageId = imageId;
        this.answerProgamerId = answerProgamerId;
        this.answerProgamerTag = answerProgamerTag;
        this.answerProgamerPosition = answerProgamerPosition;
        this.rosters = rosters;
    }

    public static LOLPieceOfPuzzleQuizResponse of(Long id, Long index, String teamName, Long seasonYear, Long imageId, Long answerProgamerId, String answerProgamerTag, Position answerProgamerPosition, List<ProgamerLOLSimpleInfoResponse> rosters) {
        return new LOLPieceOfPuzzleQuizResponse(id, index, teamName, seasonYear, imageId, answerProgamerId, answerProgamerTag, answerProgamerPosition, rosters);
    }

}
