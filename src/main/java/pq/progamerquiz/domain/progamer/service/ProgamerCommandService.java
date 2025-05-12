package pq.progamerquiz.domain.progamer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.repository.ProgamerRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerCommandService {

    private final ProgamerRepository progamerRepository;

}
