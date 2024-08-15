package com.pq.progamerquiz.domain.whoareyou;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Quiz1Repository extends JpaRepository<Quiz1, Long> {
    // 기본적인 CRUD 연산은 JpaRepository에서 제공
}

