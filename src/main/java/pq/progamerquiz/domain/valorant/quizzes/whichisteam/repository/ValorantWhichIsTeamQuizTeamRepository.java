package pq.progamerquiz.domain.valorant.quizzes.whichisteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.entity.ValorantWhichIsTeamQuizTeam;

import java.util.List;

public interface ValorantWhichIsTeamQuizTeamRepository extends JpaRepository<ValorantWhichIsTeamQuizTeam, Long> {

    @Query("SELECT witqt FROM ValorantWhichIsTeamQuizTeam witqt JOIN FETCH witqt.teamValorant WHERE witqt.whichisteam.id = :whichIsTeamId")
    List<ValorantWhichIsTeamQuizTeam> findByWhichIsTeamIdWithTeam(@Param("whichIsTeamId") Long whichIsTeamId);
}
