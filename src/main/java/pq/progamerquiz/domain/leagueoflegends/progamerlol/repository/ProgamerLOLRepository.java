package pq.progamerquiz.domain.leagueoflegends.progamerlol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgamerLOLRepository extends JpaRepository<ProgamerLOL, Long> {

    @Query("SELECT p FROM ProgamerLOL p ORDER BY RAND() LIMIT 1")
    ProgamerLOL findRandomProgamer();

    @Query("SELECT p FROM ProgamerLOL p WHERE LOWER(p.progamerTag) LIKE LOWER(:progamerTag)")
    Optional<ProgamerLOL> findByProgamerTag(String progamerTag);

    @Query("SELECT p FROM ProgamerLOL p WHERE p.id IN :ids ORDER BY RAND() LIMIT 1")
    Optional<ProgamerLOL> findOneRandomProgamer(List<Long> ids);

}
