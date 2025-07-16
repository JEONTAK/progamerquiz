package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

@Getter
public class LOLPieceOfPuzzleSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private LOLPieceOfPuzzleSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static LOLPieceOfPuzzleSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new LOLPieceOfPuzzleSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
