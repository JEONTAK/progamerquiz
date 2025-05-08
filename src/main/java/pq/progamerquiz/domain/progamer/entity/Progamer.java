package pq.progamerquiz.domain.progamer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Position;

import java.time.LocalDate;

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
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private Long leagueWin;

    @Column(nullable = false)
    private Long intlWin;

    @Column(nullable = false)
    private String nationality;

/*
    @ManyToMany  // 중간 테이블을 활용한 다대다 매핑
    @JoinTable(
            name = "progamer_team",
            joinColumns = @JoinColumn(name = "progamer_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();*/

}

