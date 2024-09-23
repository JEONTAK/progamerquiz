package pq.progamerquiz.progamer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    Progamer findByPid(String pid);
    @Query("SELECT p FROM Progamer p WHERE LOWER(p.pid) = LOWER(:pid)")
    Progamer findByPidIgnoreCase(@Param("pid") String pid);
}
