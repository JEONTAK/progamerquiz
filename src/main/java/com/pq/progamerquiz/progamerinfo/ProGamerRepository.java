package com.pq.progamerquiz.progamerinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProGamerRepository extends JpaRepository<ProGamer, Long> {

    ProGamer findByPlayerId(String playerId);

    ProGamer findByName(String name);

}
