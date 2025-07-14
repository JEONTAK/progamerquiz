package pq.progamerquiz.domain.leagueoflegends.teamlol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

import java.util.List;

@Repository
public interface TeamLOLRepository extends JpaRepository<TeamLOL, Long> {

    @Query("SELECT pt.teamLOL FROM ProgamerTeamLOL pt " +
            "WHERE pt.progamerLOL.id = :progamerId " +
            "ORDER BY pt.teamLOL.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    TeamLOL findLatestTeamByProgamerId(Long progamerId);

    @Query("SELECT pt.teamLOL FROM ProgamerTeamLOL pt " +
            "WHERE pt.progamerLOL.progamerTag = :progamerTag " +
            "ORDER BY pt.teamLOL.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    TeamLOL findLatestTeamByProgamerTag(String progamerTag);

    @Query("SELECT t FROM TeamLOL t WHERE t.id IN :teamIds ORDER BY RAND() LIMIT :count")
    List<TeamLOL> findRandomTeam(@Param("teamIds") List<Long> teamIds, @Param("count") Integer count);

    List<TeamLOL> findAllByName(String name);
}
