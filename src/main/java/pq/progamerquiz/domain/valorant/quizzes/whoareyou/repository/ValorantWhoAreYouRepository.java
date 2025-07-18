package pq.progamerquiz.domain.valorant.quizzes.whoareyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.valorant.quizzes.whoareyou.entity.ValorantWhoAreYou;

import java.util.Optional;

public interface ValorantWhoAreYouRepository extends JpaRepository<ValorantWhoAreYou, Long> {

    @Query("SELECT way FROM ValorantWhoAreYou way JOIN FETCH way.quizProgamerValorant WHERE way.id = :id")
    Optional<ValorantWhoAreYou> findByIdWIthProgamer(Long id);

    @Modifying
    @Query("UPDATE ValorantWhoAreYou way SET way.attempt = :attempts, way.correct = :isCorrect WHERE way.id = :id")
    void updateCorrectQuizCount(Long id, Long attempts, boolean isCorrect);

}
