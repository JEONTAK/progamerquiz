package pq.progamerquiz.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "teams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue
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
    private Long springRank;

    @Column
    private Long summerRank;

    @Column
    private Long msiRank;

    @Column
    private Long worldsRank;

    @Column
    private Long winterRank;

    @Column(nullable = false)
    public Long imageId;

    private Team(Long id, String name, String callName, Long seasonYear, String league, Long springRank, Long summerRank, Long msiRank, Long worldsRank, Long winterRank, Long imageId) {
        this.id = id;
        this.name = name;
        this.callName = callName;
        this.seasonYear = seasonYear;
        this.league = league;
        this.springRank = springRank;
        this.summerRank = summerRank;
        this.msiRank = msiRank;
        this.worldsRank = worldsRank;
        this.winterRank = winterRank;
        this.imageId = imageId;
    }

    public static Team create(Long id, String name, String callName, Long seasonYear, String league, Long springRank, Long summerRank, Long msiRank, Long worldsRank, Long winterRank, Long imageId) {
        return new Team(id, name, callName, seasonYear, league, springRank, summerRank, msiRank, worldsRank, winterRank, imageId);
    }
}