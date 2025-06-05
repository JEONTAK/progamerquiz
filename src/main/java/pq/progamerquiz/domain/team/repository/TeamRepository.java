package pq.progamerquiz.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.team.entity.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT pt.team FROM ProgamerTeam pt " +
            "WHERE pt.progamer.id = :progamerId " +
            "ORDER BY pt.team.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    Team findLatestTeamByProgamerId(Long progamerId);

    @Query("SELECT pt.team FROM ProgamerTeam pt " +
            "WHERE pt.progamer.progamerTag = :progamerTag " +
            "ORDER BY pt.team.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    Team findLatestTeamByProgamerTag(String progamerTag);

    @Query("SELECT t FROM Team t WHERE t.id IN :teamIds ORDER BY RAND() LIMIT :count")
    List<Team> findRandomTeam(@Param("teamIds") List<Long> teamIds, @Param("count") Integer count);

    List<Team> findAllByName(String name);
}
