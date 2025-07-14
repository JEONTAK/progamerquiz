package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class PieceOfPuzzleSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private PieceOfPuzzleSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static PieceOfPuzzleSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new PieceOfPuzzleSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
