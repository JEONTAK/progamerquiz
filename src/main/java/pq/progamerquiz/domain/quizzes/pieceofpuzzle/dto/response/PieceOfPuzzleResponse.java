package pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class PieceOfPuzzleResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<PieceOfPuzzleQuizResponse> quizList;

    private PieceOfPuzzleResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<PieceOfPuzzleQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static PieceOfPuzzleResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<PieceOfPuzzleQuizResponse> list) {
        return new PieceOfPuzzleResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
