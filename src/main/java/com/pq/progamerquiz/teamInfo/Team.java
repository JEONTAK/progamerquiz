package com.pq.progamerquiz.teamInfo;

import com.pq.progamerquiz.progamerInfo.Progamer;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 10)
    private String ty;

    private Integer lr; // Last Week Rank
    private Integer mr; // Mid Season Rank
    private Integer wr; // Worlds Rank

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "progamer")
    private List<Progamer> roster = new ArrayList<>();

    // Getters and Setters...
}
