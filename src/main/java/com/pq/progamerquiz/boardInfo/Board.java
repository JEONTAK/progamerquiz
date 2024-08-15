package com.pq.progamerquiz.boardInfo;
import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer league = -1;

    @Column(nullable = false)
    private Integer team = -1;

    @Column(nullable = false)
    private Integer pos = -1;

    @Column(nullable = false)
    private Integer lw = -1; // Last Week

    @Column(nullable = false)
    private Integer iw = -1; // Current Week

    // 생성자에서 비즈니스 로직 적용
    public Board(Integer league, Integer team, Integer pos, Integer lw, Integer iw) {
        int nonDefaultCount = 0;
        if (league != null && league != -1) nonDefaultCount++;
        if (team != null && team != -1) nonDefaultCount++;
        if (pos != null && pos != -1) nonDefaultCount++;
        if (lw != null && lw != -1) nonDefaultCount++;
        if (iw != null && iw != -1) nonDefaultCount++;

        if (nonDefaultCount != 1) {
            throw new IllegalArgumentException("하나의 필드에만 값이 설정되어야 하며 나머지는 -1이어야 합니다.");
        }

        this.league = (league != null) ? league : -1;
        this.team = (team != null) ? team : -1;
        this.pos = (pos != null) ? pos : -1;
        this.lw = (lw != null) ? lw : -1;
        this.iw = (iw != null) ? iw : -1;
    }

    // 기본 생성자 (JPA 용)
    protected Board() {
    }

    // Setter 메서드 (이러한 메서드를 통해 값 변경 시에도 검증 로직 적용)
    public void setLeague(Integer league) {
        validateSingleNonDefaultValue(league, this.team, this.pos, this.lw, this.iw);
        this.league = league;
    }

    public void setTeam(Integer team) {
        validateSingleNonDefaultValue(this.league, team, this.pos, this.lw, this.iw);
        this.team = team;
    }

    public void setPos(Integer pos) {
        validateSingleNonDefaultValue(this.league, this.team, pos, this.lw, this.iw);
        this.pos = pos;
    }

    public void setLw(Integer lw) {
        validateSingleNonDefaultValue(this.league, this.team, this.pos, lw, this.iw);
        this.lw = lw;
    }

    public void setIw(Integer iw) {
        validateSingleNonDefaultValue(this.league, this.team, this.pos, this.lw, iw);
        this.iw = iw;
    }

    private void validateSingleNonDefaultValue(Integer league, Integer team, Integer pos, Integer lw, Integer iw) {
        int nonDefaultCount = 0;
        if (league != null && league != -1) nonDefaultCount++;
        if (team != null && team != -1) nonDefaultCount++;
        if (pos != null && pos != -1) nonDefaultCount++;
        if (lw != null && lw != -1) nonDefaultCount++;
        if (iw != null && iw != -1) nonDefaultCount++;

        if (nonDefaultCount > 1) {
            throw new IllegalArgumentException("하나의 필드에만 값이 설정되어야 하며 나머지는 -1이어야 합니다.");
        }
    }

    // Getters and Setters...
}
