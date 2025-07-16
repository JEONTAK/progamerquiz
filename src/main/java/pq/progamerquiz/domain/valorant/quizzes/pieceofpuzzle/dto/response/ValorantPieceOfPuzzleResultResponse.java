package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class ValorantPieceOfPuzzleResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private ValorantPieceOfPuzzleResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static ValorantPieceOfPuzzleResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new ValorantPieceOfPuzzleResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
