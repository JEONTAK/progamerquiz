package pq.progamerquiz.progamer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

    List<Progamer> findByName(String name);
    List<Progamer> findByPid(String pid);
    List<Progamer> findByNameOrPid(String name, String pid);
}
