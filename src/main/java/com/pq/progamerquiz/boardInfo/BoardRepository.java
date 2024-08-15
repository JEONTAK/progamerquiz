package com.pq.progamerquiz.boardInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 기본적인 CRUD 연산은 JpaRepository에서 제공

    // 예시: 특정 리그, 팀, 포지션, LW, IW 값을 가진 보드를 찾는 메서드
    Board findByLeagueOrTeamOrPosOrLwOrIw(Integer league, Integer team, Integer pos, Integer lw, Integer iw);
}

