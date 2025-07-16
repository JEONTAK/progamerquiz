package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.entity.LOLWhoAreYou;

import java.util.Optional;

public interface LOLWhoAreYouRepository extends JpaRepository<LOLWhoAreYou, Long> {

    @Query("SELECT way FROM LOLWhoAreYou way JOIN FETCH way.quizProgamerLOL WHERE way.id = :id")
    Optional<LOLWhoAreYou> findByIdWIthProgamer(Long id);

}
