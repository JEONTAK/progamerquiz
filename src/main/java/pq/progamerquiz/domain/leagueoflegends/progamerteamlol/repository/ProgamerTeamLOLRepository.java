package pq.progamerquiz.domain.leagueoflegends.progamerteamlol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.entity.ProgamerTeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

import java.util.List;

public interface ProgamerTeamLOLRepository extends JpaRepository<ProgamerTeamLOL, Long> {

    @Query("SELECT t from TeamLOL t" +
            "")
    TeamLOL findLatestTeamByProgamerId(Long progamerId);


    @Query("SELECT new pq.progamerquiz.domain.leagueoflegends.teamlol.dto" +
            ".response.TeamSimpleInfoResponse(pt.teamLOL.id, pt.teamLOL.name, pt.teamLOL.callName, pt.teamLOL.seasonYear, pt.teamLOL.imageId) FROM ProgamerTeamLOL pt " +
            "WHERE pt.progamerLOL.id = :progamerId " +
            "ORDER BY pt.teamLOL.seasonYear ASC")
    List<TeamSimpleInfoResponse> findTeamsByProgamerId(@Param("progamerId") Long progamerId);

    @Query("SELECT new pq.progamerquiz.domain.leagueoflegends.progamerlol.dto" +
            ".response.ProgamerSimpleInfoResponse(pt.progamerLOL.id, pt.progamerLOL.progamerTag) FROM ProgamerTeamLOL pt " +
            "WHERE pt.teamLOL.id = :teamId ")
    List<ProgamerSimpleInfoResponse> findProgamersByTeamId(Long teamId);

    @Query("SELECT pt.teamLOL.id FROM ProgamerTeamLOL pt GROUP BY pt.teamLOL.id HAVING COUNT(pt.progamerLOL) >= 5")
    List<Long> findTeamIdsWithFiveOrMoreProgamers();

}
