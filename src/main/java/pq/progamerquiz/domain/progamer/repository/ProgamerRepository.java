package pq.progamerquiz.domain.progamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.progamer.entity.Progamer;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    @Query("SELECT p FROM Progamer p ORDER BY RAND() LIMIT 1")
    Progamer findRandomProgamer();

    @Query("SELECT p FROM Progamer p WHERE LOWER(p.progamerTag) LIKE LOWER(:progamerTag)")
    Optional<Progamer> findByProgamerTag(String progamerTag);

    @Query("SELECT p FROM Progamer p WHERE p.id IN :ids ORDER BY RAND() LIMIT 1")
    Optional<Progamer> findOneRandomProgamer(List<Long> ids);

}
