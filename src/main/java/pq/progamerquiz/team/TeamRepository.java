package pq.progamerquiz.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByName(String name);

    @Query("SELECT t.id FROM Team t WHERE LOWER(t.name) = LOWER(:teamName)")
    Long findNameById(String teamName);

    @Query("SELECT t.id FROM Team t WHERE LOWER(t.name) = LOWER(:teamName) and :seasonYear = t.seasonYear")
    Long findIdByNameAndYear(String teamName, Long seasonYear);
}
