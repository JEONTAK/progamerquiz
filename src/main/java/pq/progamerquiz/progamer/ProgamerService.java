package pq.progamerquiz.progamer;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerService {

    @Autowired
    private final ProgamerRepository progamerRepository;

    public List<Progamer> findAll() {
        return progamerRepository.findAll();
    }

    public Progamer findOne(Long progamerId) {
        return progamerRepository.findById(progamerId).orElse(null);
    }

    public void saveProgamer(Progamer progamer) {
        progamerRepository.save(progamer);
    }
}
