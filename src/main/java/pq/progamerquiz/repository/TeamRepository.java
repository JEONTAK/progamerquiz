package pq.progamerquiz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE LOWER(t.name) = LOWER(:name)")
    List<Team> findByName(String name);


    @Query("SELECT t.id FROM Team t WHERE LOWER(t.name) = LOWER(:teamName)")
    Long findIdByName(String teamName);

    @Query("SELECT t FROM Team t WHERE (:league IS NULL OR t.league = :league) ORDER BY RAND() LIMIT :totalCount")
    List<Team> findRandomTeams(int totalCount, String league);

    @Query("SELECT t FROM Team t WHERE LOWER(t.name) = LOWER(:teamName) OR LOWER(t.callName) = LOWER(:teamName)")
    List<Team> findByNameOrCallName(String teamName);


    @Query("SELECT t.id FROM Team t WHERE (:league IS NULL OR t.league = :league) ORDER BY RAND()")
    List<Long> findRandomTeamIds(Pageable pageable, String league);

    @Query("SELECT t.id FROM Team t WHERE (:league IS NULL OR t.league = :league) AND SIZE(t.roster) >= 5 ORDER BY FUNCTION('RAND')")
    List<Long> findTeamIdsByLeagueWithRosterSize(Pageable pageable, String league);

    @EntityGraph(attributePaths = {"roster"})
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.roster WHERE t.id IN :ids")
    List<Team> findTeamsByIds(@Param("ids") List<Long> ids);

}
