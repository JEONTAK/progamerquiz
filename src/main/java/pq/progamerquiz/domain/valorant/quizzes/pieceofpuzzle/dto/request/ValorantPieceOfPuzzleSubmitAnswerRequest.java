package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValorantPieceOfPuzzleSubmitAnswerRequest {

    private Long id;
    private Integer index;
    private String input;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
