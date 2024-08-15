package com.pq.progamerquiz.progamerInfo;

import com.pq.progamerquiz.teamInfo.Team;
import jakarta.persistence.*;

@Entity
@Table(name ="progamer")
public class Progamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String pid;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tid")
    private Team team;

    @Column(nullable = false)
    private Integer birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Position pos;

    private Integer lw; // Last Week Wins
    private Integer iw; // Current Week Wins
    private String imagePath;

    // Enum for League
    public enum League {
        LCK, LPL, LEC, LCS
    }

    // Enum for Position
    public enum Position {
        TOP, JGL, MID, ADC, SPT
    }

    public void setImage(){
        this.imagePath = setImagePath(this.id);
    }

    private String setImagePath(Long id) {
        String path = "../image/content/player/";
        path = path + id + ".jpg";
        return path;
    }

}

