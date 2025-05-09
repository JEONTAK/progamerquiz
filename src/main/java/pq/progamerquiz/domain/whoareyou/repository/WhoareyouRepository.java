package pq.progamerquiz.domain.whoareyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pq.progamerquiz.domain.whoareyou.entity.Whoareyou;

public interface WhoareyouRepository extends JpaRepository<Whoareyou, Long> {
}
