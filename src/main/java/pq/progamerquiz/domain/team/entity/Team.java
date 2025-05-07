package pq.progamerquiz.domain.team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.progamer.entity.Progamer;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class  Team{

    @Id
    @GeneratedValue
    @Column(name = "team_id")
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

    @ManyToMany(mappedBy = "teams")
    private List<Progamer> roster = new ArrayList<>();

    public Long image_path;
}
