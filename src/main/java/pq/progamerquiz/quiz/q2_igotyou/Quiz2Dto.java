package pq.progamerquiz.quiz.q2_igotyou;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

//Quiz : I Got you!
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz2Dto {

    private Long index;
    private Long id;
    private String pid;
    private String name;
    private String position;
    private List<Long> teamYears;
    private List<String> teamNames;
    private List<Long> teamImages;

}
