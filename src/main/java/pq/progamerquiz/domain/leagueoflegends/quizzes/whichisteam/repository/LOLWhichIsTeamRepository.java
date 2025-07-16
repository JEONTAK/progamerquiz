package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity.LOLWhichIsTeam;

public interface LOLWhichIsTeamRepository extends JpaRepository<LOLWhichIsTeam, Long> {

    @Modifying
    @Query("UPDATE LOLWhichIsTeam wit SET wit.correctQuizCount = :correctQuizCount WHERE wit.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
