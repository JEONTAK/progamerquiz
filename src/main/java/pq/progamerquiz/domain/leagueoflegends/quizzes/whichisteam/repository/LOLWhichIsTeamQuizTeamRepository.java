package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity.LOLWhichIsTeamQuizTeam;

import java.util.List;

public interface LOLWhichIsTeamQuizTeamRepository extends JpaRepository<LOLWhichIsTeamQuizTeam, Long> {

    @Query("SELECT witqt FROM LOLWhichIsTeamQuizTeam witqt JOIN FETCH witqt.teamLOL WHERE witqt.whichisteam.id = :whichIsTeamId")
    List<LOLWhichIsTeamQuizTeam> findByWhichIsTeamIdWithTeam(@Param("whichIsTeamId") Long whichIsTeamId);
}
