package pq.progamerquiz.domain.valorant.teamvalorant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "teams_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamValorant {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String callName;

    @Column(nullable = false)
    private Long seasonYear;

    @Column(nullable = false)
    private String league;

    @Column
    private String seasonName;

    @Column
    private String seasonRank;

    private TeamValorant(Long id, String name, String callName, Long seasonYear, String league, String seasonName, String seasonRank) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.seasonName = seasonName;
        this.seasonRank = seasonRank;
    }

    public static TeamValorant create(Long id, String name, String callName, Long seasonYear, String league, String seasonName, String rank) {
        return new TeamValorant(id, name, callName, seasonYear, league, seasonName, rank);
    }
}