package pq.progamerquiz.domain.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;

@Entity
@Table(name = "igotyou_progamer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IgotyouProgamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igotyou_id", nullable = false)
    private Igotyou igotyou;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer progamer;
}
