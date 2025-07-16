package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class LOLPieceOfPuzzleResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private LOLPieceOfPuzzleResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLPieceOfPuzzleResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLPieceOfPuzzleResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
