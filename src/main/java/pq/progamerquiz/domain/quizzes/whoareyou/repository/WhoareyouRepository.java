package pq.progamerquiz.domain.quizzes.whoareyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pq.progamerquiz.domain.quizzes.whoareyou.entity.Whoareyou;

public interface WhoareyouRepository extends JpaRepository<Whoareyou, Long> {
}
