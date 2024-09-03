package pq.progamerquiz.board;
import jakarta.persistence.*;

import jakarta.persistence.*;
import java.lang.reflect.Type;
import pq.progamerquiz.team.Team.League;

@Entity
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private HintType hintType;

    private String hint;

    public enum HintType {
        league, team, position, leagueWin, intlWin, coPlayer
    }
}
