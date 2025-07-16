package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class ValorantPieceOfPuzzleSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private ValorantPieceOfPuzzleSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static ValorantPieceOfPuzzleSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new ValorantPieceOfPuzzleSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
