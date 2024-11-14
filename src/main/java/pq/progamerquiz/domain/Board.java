package pq.progamerquiz.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
