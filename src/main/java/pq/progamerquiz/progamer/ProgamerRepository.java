package pq.progamerquiz.progamer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    Optional<Progamer> findByName(String name);
    Progamer findByPid(String pid);
    Optional<Progamer> findByNameOrPid(String name, String pid);
}
