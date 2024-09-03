package pq.progamerquiz.progamer;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pq.progamerquiz.team.Team;

@Entity
public class ProgamerTeam extends Progamer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long year;

    @ManyToOne
    @JoinColumn(name = "progamer_id")
    private Progamer progamer;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Getters and Setters
}
