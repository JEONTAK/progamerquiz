package pq.progamerquiz.domain.progamer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.whoareyou.entity.Whoareyou;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private Long league_win;
    @Column(nullable = false)
    private Long intl_win;
    @Column(nullable = false)
    private String nationality;

    @ManyToMany  // 중간 테이블을 활용한 다대다 매핑
    @JoinTable(
            name = "progamer_team",
            joinColumns = @JoinColumn(name = "progamer_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    public enum Position {
        TOP, JGL, MID, ADC, SUP,
    }

    @OneToMany(mappedBy = "quizProgamer")
    private Collection<Whoareyou> whoareyou;

    public Collection<Whoareyou> getWhoareyou() {
        return whoareyou;
    }

    public void setWhoareyou(Collection<Whoareyou> whoareyou) {
        this.whoareyou = whoareyou;
    }
}

