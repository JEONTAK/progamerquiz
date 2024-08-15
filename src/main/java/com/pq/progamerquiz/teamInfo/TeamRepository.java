package com.pq.progamerquiz.teamInfo;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {

    // 팀 이름으로 팀을 찾는 메서드
    Team findByName(String name);

    // 특정 년도에 설립된 팀을 찾는 메서드
    Team findByTy(String ty);

}
