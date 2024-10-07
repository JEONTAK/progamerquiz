package pq.progamerquiz.quiz.q4_pieceofpuzzle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Quiz : What is Team?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz4Dto {

    private Long index;
    private Long teamId;
    private String teamName;
    private Long teamYear;
    private List<ProgamerDto> roster = new ArrayList<>();
    private List<Map<Long, Boolean>> answer = new ArrayList<>();
    private Long image_path;
}
