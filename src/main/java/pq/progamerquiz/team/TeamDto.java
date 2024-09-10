package pq.progamerquiz.team;

import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamDto {

    private Long id;
    private String name;
    private String callName;
    private Long year;
    private String league;
    private Long spring_rank;
    private Long summer_rank;
    private Long msi_rank;
    private Long worlds_rank;
    private Long winter_rank;
    private List<Progamer> roster = new ArrayList<>();
}
