package pq.progamerquiz.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findByName(String name);

    @Query("SELECT t.id FROM Team t WHERE LOWER(t.name) = LOWER(:teamName)")
    Long findNameById(String teamName);
}
