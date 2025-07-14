package pq.progamerquiz.domain.leagueoflegends.progamerlol.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Position;

@Entity
@Table(name = "progamers_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerLOL {

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

    private ProgamerLOL(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.leagueWin = leagueWin;
        this.intlWin = intlWin;
        this.nationality = nationality;
    }

    public static ProgamerLOL create(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality) {
        return new ProgamerLOL(id, progamerTag, name, birth, position, leagueWin, intlWin, nationality);
    }
}

