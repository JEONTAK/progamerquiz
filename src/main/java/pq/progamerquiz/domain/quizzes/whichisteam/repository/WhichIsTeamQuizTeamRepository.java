package pq.progamerquiz.domain.quizzes.whichisteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.quizzes.whichisteam.entity.WhichIsTeamQuizTeam;

import java.util.List;

public interface WhichIsTeamQuizTeamRepository extends JpaRepository<WhichIsTeamQuizTeam, Long> {

    @Query("SELECT witqt FROM WhichIsTeamQuizTeam witqt JOIN FETCH witqt.team WHERE witqt.whichisteam.id = :whichIsTeamId")
    List<WhichIsTeamQuizTeam> findByWhichIsTeamIdWithTeam(@Param("whichIsTeamId") Long whichIsTeamId);
}
