package pq.progamerquiz.team;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamDto {

    private Long id;
    private String name;
    private String callName;
    private Long seasonYear;
    private String league;
    private Long spring_rank;
    private Long summer_rank;
    private Long msi_rank;
    private Long worlds_rank;
    private Long winter_rank;
    private List<Progamer> roster = new ArrayList<>();
    private Long image_path;
}
