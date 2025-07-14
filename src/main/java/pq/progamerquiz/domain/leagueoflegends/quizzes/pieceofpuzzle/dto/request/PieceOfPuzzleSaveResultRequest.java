package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PieceOfPuzzleSaveResultRequest {

    private Long id;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
