package pq.progamerquiz.dto;

import lombok.*;

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
    private int correct;
    private int attempts;

}
