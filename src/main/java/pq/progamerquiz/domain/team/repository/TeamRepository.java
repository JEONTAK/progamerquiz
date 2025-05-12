package pq.progamerquiz.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.team.entity.Team;

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
}
