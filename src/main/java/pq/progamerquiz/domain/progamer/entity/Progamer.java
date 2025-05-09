package pq.progamerquiz.domain.progamer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Position;

@Entity
@Table(name = "progamers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progamer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String progamerTag;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Long leagueWin;

    private Long intlWin;

    @Column(nullable = false)
    private String nationality;

    private Progamer(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.leagueWin = leagueWin;
        this.intlWin = intlWin;
        this.nationality = nationality;

    }

    public static Progamer create(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality) {
        return new Progamer(id, progamerTag, name, birth, position, leagueWin, intlWin, nationality);
    }
}

