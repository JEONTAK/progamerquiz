package pq.progamerquiz.domain.leagueoflegends.progamerlol.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.repository.ProgamerLOLRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerLOLCommandService {

    private final ProgamerLOLRepository progamerLOLRepository;

}
