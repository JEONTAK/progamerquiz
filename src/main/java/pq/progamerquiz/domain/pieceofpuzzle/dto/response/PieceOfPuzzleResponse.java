package pq.progamerquiz.domain.pieceofpuzzle.dto.response;

import lombok.*;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PieceOfPuzzleResponse {

    private Long index;
    private Long teamId;
    private String teamName;
    private Long teamYear;
    private List<ProgamerDto> roster = new ArrayList<>();
    private List<Map<Long, Boolean>> answer = new ArrayList<>();
    private Long image_path;
    private int correct;
    private int attempts;


    public void updateAttempts() {
        this.attempts++;
    }

    public void updateCorrect() {
        this.correct++;
    }
}
