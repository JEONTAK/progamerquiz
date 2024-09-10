package pq.progamerquiz.progamer;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

}
