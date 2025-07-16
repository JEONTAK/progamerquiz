package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValorantPieceOfPuzzleSaveResultRequest {

    private Long id;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
