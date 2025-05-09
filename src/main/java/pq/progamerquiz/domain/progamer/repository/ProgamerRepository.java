package pq.progamerquiz.domain.progamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.progamer.entity.Progamer;

@Repository
public interface ProgamerRepository extends JpaRepository<Progamer, Long> {

}
