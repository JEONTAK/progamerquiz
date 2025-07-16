package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class ValorantPieceOfPuzzleResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<ValorantPieceOfPuzzleQuizResponse> quizList;

    private ValorantPieceOfPuzzleResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantPieceOfPuzzleQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static ValorantPieceOfPuzzleResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantPieceOfPuzzleQuizResponse> list) {
        return new ValorantPieceOfPuzzleResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
