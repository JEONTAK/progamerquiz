package pq.progamerquiz.domain.valorant.progamerteamvalorant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.entity.ProgamerTeamValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantSimpleInfoResponse;

import java.util.List;

public interface ProgamerTeamValorantRepository extends JpaRepository<ProgamerTeamValorant, Long> {

    @Query("SELECT t from TeamValorant t" +
            "")
    TeamLOL findLatestTeamByProgamerId(Long progamerId);


    @Query("SELECT new pq.progamerquiz.domain.valorant.teamvalorant.dto" +
            ".response.TeamValorantSimpleInfoResponse(pt.teamValorant.id, pt.teamValorant.name, pt.teamValorant.callName, pt.teamValorant.seasonName, pt.teamValorant.seasonYear) FROM ProgamerTeamValorant pt " +
            "WHERE pt.progamerValorant.id = :progamerId " +
            "ORDER BY pt.teamValorant.seasonYear ASC")
    List<TeamValorantSimpleInfoResponse> findTeamsByProgamerId(@Param("progamerId") Long progamerId);

    @Query("SELECT new pq.progamerquiz.domain.valorant.progamervalorant.dto" +
            ".response.ProgamerValorantSimpleInfoResponse(pt.progamerValorant.id, pt.progamerValorant.progamerTag) FROM ProgamerTeamValorant pt " +
            "WHERE pt.teamValorant.id = :teamId ")
    List<ProgamerValorantSimpleInfoResponse> findProgamersByTeamId(Long teamId);

    @Query("SELECT pt.teamValorant.id FROM ProgamerTeamValorant pt GROUP BY pt.teamValorant.id HAVING COUNT(pt.progamerValorant) >= 5")
    List<Long> findTeamIdsWithFiveOrMoreProgamers();

}
