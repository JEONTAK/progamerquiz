package com.pq.progamerquiz.progamerInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    // 특정 PID로 프로게이머를 찾는 메서드
    Progamer findByPid(String pid);

    // 특정 팀의 모든 프로게이머를 찾는 메서드
    //List<Progamer> findByTeamId(Long teamId);

    // 특정 포지션의 프로게이머를 찾는 메서드
    List<Progamer> findByPos(Progamer.Position pos);

    // 특정 리그의 모든 프로게이머를 찾는 메서드
    List<Progamer> findByLeague(Progamer.League league);

    // 특정 포지션과 리그의 프로게이머를 찾는 메서드
    List<Progamer> findByPosAndLeague(Progamer.Position pos, Progamer.League league);

    Progamer findByName(String idOrName);
}
