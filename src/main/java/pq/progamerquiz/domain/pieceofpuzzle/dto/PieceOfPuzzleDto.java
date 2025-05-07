package pq.progamerquiz.domain.pieceofpuzzle.dto;

import lombok.*;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Quiz : What is Team?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieceOfPuzzleDto {

    private Long index;
    private Long teamId;
    private String teamName;
    private Long teamYear;
    private List<ProgamerDto> roster = new ArrayList<>();
    private List<Map<Long, Boolean>> answer = new ArrayList<>();
    private Long image_path;
    private int correct;
    private int attempts;

}
