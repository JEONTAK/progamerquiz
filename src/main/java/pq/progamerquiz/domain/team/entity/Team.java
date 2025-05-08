package pq.progamerquiz.domain.team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "teams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String callName;

    @Column(nullable = false)
    private Long seasonYear;

    @Column(nullable = false)
    private String league;

    @Column
    private Long springRank;

    @Column
    private Long summerRank;

    @Column
    private Long msiRank;

    @Column
    private Long worldsRank;

    @Column
    private Long winterRank;

    @Column(nullable = false)
    public Long imageId;

    /*    @ManyToMany(mappedBy = "teams")
    private List<Progamer> roster = new ArrayList<>();*/
}
