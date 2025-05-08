package pq.progamerquiz.domain.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "igotyou")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Igotyou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    @OneToMany(mappedBy = "igotyou", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IgotyouProgamer> quizProgamerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer quizProgamer;
}
