package pq.progamerquiz.domain.valorant.teamvalorant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

import java.util.List;

@Repository
public interface TeamValorantRepository extends JpaRepository<TeamValorant, Long> {

    @Query("SELECT pt.teamValorant FROM ProgamerTeamValorant pt " +
            "WHERE pt.progamerValorant.id = :progamerId " +
            "ORDER BY pt.teamValorant.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    TeamValorant findLatestTeamByProgamerId(Long progamerId);

    @Query("SELECT pt.teamValorant FROM ProgamerTeamValorant pt " +
            "WHERE pt.progamerValorant.progamerTag = :progamerTag " +
            "ORDER BY pt.teamValorant.seasonYear DESC " +
            "FETCH FIRST 1 ROWS ONLY")
    TeamValorant findLatestTeamByProgamerTag(String progamerTag);

    @Query("SELECT t FROM TeamValorant t WHERE t.id IN :teamIds ORDER BY RAND() LIMIT :count")
    List<TeamValorant> findRandomTeam(@Param("teamIds") List<Long> teamIds, @Param("count") Integer count);

    List<TeamValorant> findAllByName(String name);
}
