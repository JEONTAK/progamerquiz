package pq.progamerquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}

