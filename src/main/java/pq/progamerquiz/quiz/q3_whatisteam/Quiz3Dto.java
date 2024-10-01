package pq.progamerquiz.quiz.q3_whatisteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;

import java.util.List;

//Quiz : What is Team?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz3Dto {

    private Long index;
    private Long teamId;
    private String teamName;
    private String teamLeague;
    private Long teamYear;
    private Long spring_ranking;
    private Long summer_ranking;
    private Long winter_ranking;
    private Long worlds_ranking;
    private Long msi_ranking;
    private Long image_path;
}
