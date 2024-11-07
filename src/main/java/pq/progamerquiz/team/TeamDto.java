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

    public TeamDto(Long id, String name, String league, Long seasonYear, List<Progamer> roster, Long image_path) {
        this.id = id;
        this.name = name;
        this.league = league;
        this.seasonYear = seasonYear;
        this.roster = roster;
        this.image_path = image_path;
    }

    public TeamDto(Long id, String name, String callName, Long seasonYear, String league, Long spring_rank, Long summer_rank, Long msi_rank, Long worlds_rank, Long winter_rank, List<Progamer> roster, Long image_path) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.spring_rank = spring_rank;
        this.summer_rank = summer_rank;
        this.msi_rank = msi_rank;
        this.worlds_rank = worlds_rank;
        this.winter_rank = winter_rank;
        this.roster = roster;
        this.image_path = image_path;
    }
}
