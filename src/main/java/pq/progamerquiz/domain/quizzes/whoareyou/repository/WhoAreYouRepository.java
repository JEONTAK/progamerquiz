package pq.progamerquiz.domain.quizzes.whoareyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.quizzes.whoareyou.entity.WhoAreYou;

import java.util.Optional;

public interface WhoAreYouRepository extends JpaRepository<WhoAreYou, Long> {

    @Query("SELECT way FROM WhoAreYou way JOIN FETCH way.quizProgamer WHERE way.id = :id")
    Optional<WhoAreYou> findByIdWIthProgamer(Long id);

}
