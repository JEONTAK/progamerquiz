package pq.progamerquiz.service;


import pq.progamerquiz.repository.ProgamerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgamerService {

    private final ProgamerRepository progamerRepository;

}
