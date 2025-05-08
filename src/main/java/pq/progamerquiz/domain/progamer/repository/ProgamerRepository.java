package pq.progamerquiz.domain.progamer.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.progamer.entity.Progamer;

import java.util.List;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {
    @Query("SELECT p FROM Progamer p LEFT JOIN FETCH p.teams WHERE lower(p.progamerTag) = lower(:pid)")
    Progamer findByPidIgnoreCase(@Param("pid") String pid);

    @Query("SELECT p FROM Progamer p ORDER BY RAND() LIMIT :totalCount")
    List<Progamer> findRandomPlayers(int totalCount);

    @Query("SELECT p.id FROM Progamer p ORDER BY RAND()")
    List<Long> findRandomProgamerIds(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Progamer p LEFT JOIN FETCH p.teams WHERE p.id IN :ids")
    List<Progamer> findProgamersWithTeamsByIds(@Param("ids") List<Long> ids);

}
