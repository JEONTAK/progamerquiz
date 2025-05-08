package pq.progamerquiz.domain.whoareyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.entity.BaseEntity;
import pq.progamerquiz.domain.progamer.entity.Progamer;

@Entity
@Table(name = "whoareyou")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Whoareyou extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer attempt;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer quizProgamer;

}
