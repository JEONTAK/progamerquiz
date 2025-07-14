package pq.progamerquiz.domain.valorant.progamervalorant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgamerValorantRepository extends JpaRepository<ProgamerValorant, Long> {

    @Query("SELECT p FROM ProgamerLOL p ORDER BY RAND() LIMIT 1")
    ProgamerValorant findRandomProgamer();

    @Query("SELECT p FROM ProgamerLOL p WHERE LOWER(p.progamerTag) LIKE LOWER(:progamerTag)")
    Optional<ProgamerValorant> findByProgamerTag(String progamerTag);

    @Query("SELECT p FROM ProgamerLOL p WHERE p.id IN :ids ORDER BY RAND() LIMIT 1")
    Optional<ProgamerValorant> findOneRandomProgamer(List<Long> ids);

}
