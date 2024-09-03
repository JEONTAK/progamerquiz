package pq.progamerquiz.team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TeamInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long year;

    private Long leagueRank;

    private Long msiRank;

    private Long worldsRank;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
