package pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pieceofpuzzle")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pieceofpuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    @OneToMany(mappedBy = "pieceofpuzzle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PieceofpuzzleTeam> quizTeamList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer quizProgamer;
}
