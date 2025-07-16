package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class LOLPieceOfPuzzleResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<LOLPieceOfPuzzleQuizResponse> quizList;

    private LOLPieceOfPuzzleResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<LOLPieceOfPuzzleQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static LOLPieceOfPuzzleResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<LOLPieceOfPuzzleQuizResponse> list) {
        return new LOLPieceOfPuzzleResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
