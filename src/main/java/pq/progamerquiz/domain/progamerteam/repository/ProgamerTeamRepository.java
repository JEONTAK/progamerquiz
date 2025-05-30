package pq.progamerquiz.domain.progamerteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.progamerteam.entity.ProgamerTeam;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;
import pq.progamerquiz.domain.team.entity.Team;

import java.util.List;

public interface ProgamerTeamRepository extends JpaRepository<ProgamerTeam, Long> {

    @Query("SELECT t from Team t" +
            "")
    Team findLatestTeamByProgamerId(Long progamerId);


    @Query("SELECT new pq.progamerquiz.domain.team.dto" +
            ".response.TeamSimpleInfoResponse(pt.team.id, pt.team.name, pt.team.callName, pt.team.seasonYear, pt.team.imageId) FROM ProgamerTeam pt " +
            "WHERE pt.progamer.id = :progamerId " +
            "ORDER BY pt.team.seasonYear ASC")
    List<TeamSimpleInfoResponse> findTeamsByProgamerId(@Param("progamerId") Long progamerId);
}
