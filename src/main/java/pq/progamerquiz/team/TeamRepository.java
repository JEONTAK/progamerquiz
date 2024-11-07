package pq.progamerquiz.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE LOWER(t.name) = LOWER(:name)")
    List<Team> findByName(String name);

    @Query("SELECT t.id FROM Team t WHERE LOWER(t.name) = LOWER(:teamName)")
    Long findNameById(String teamName);


    @Query("SELECT t FROM Team t WHERE (:league IS NULL OR t.league = :league) ORDER BY RAND() LIMIT :totalCount")
    List<Team> findRandomTeams(int totalCount, String league);

    @Query("SELECT t FROM Team t WHERE LOWER(t.name) = LOWER(:teamName) OR LOWER(t.callName) = LOWER(:teamName)")
    List<Team> findByNameOrCallName(String teamName);
}
