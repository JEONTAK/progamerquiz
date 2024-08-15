package com.pq.progamerquiz.teamInfo;

import com.pq.progamerquiz.progamerInfo.Progamer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "team")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rId")
    private Roster roster;

    // Getters and Setters...
}
