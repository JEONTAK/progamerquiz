package pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class PieceOfPuzzleResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private PieceOfPuzzleResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static PieceOfPuzzleResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new PieceOfPuzzleResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
