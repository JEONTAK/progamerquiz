package pq.progamerquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.Progamer;

import java.util.List;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    @Query("SELECT p FROM Progamer p WHERE LOWER(p.pid) = LOWER(:pid)")
    Progamer findByPidIgnoreCase(@Param("pid") String pid);

    @Query("SELECT p FROM Progamer p ORDER BY RAND() LIMIT :totalCount")
    List<Progamer> findRandomPlayers(int totalCount);
}
