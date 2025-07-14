package pq.progamerquiz.domain.valorant.progamervalorant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "progamers_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerValorant {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String progamerTag;

    @Column(nullable = false)
    private String name;

    private String birth;

    private Long leagueWin;

    private Long mastersWin;

    private Long championsWin;

    @Column(nullable = false)
    private String nationality;

    private ProgamerValorant(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin, String nationality) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.leagueWin = leagueWin;
        this.mastersWin = mastersWin;
        this.championsWin = championsWin;
        this.nationality = nationality;
    }

    public static ProgamerValorant create(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin,String nationality) {
        return new ProgamerValorant(id, progamerTag, name, birth, leagueWin, mastersWin, championsWin, nationality);
    }
}

