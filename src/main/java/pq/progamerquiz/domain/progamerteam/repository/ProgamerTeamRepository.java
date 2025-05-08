package pq.progamerquiz.domain.progamerteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.progamerteam.entity.ProgamerTeam;
import pq.progamerquiz.domain.team.entity.Team;

public interface ProgamerTeamRepository extends JpaRepository<ProgamerTeam, Long> {

    @Query("SELECT t from Team t" +
            "")
    Team findLatestTeamByProgamerId(Long progamerId);

}
