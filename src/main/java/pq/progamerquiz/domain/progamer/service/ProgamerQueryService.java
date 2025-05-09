package pq.progamerquiz.domain.progamer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.repository.ProgamerRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgamerQueryService {

    private final ProgamerRepository progamerRepository;

    public Progamer findRandomProgamer() {
        return progamerRepository.findRandomProgamer();
    }

}
