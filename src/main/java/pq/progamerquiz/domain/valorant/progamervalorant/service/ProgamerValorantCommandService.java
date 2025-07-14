package pq.progamerquiz.domain.valorant.progamervalorant.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.valorant.progamervalorant.repository.ProgamerValorantRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerValorantCommandService {

    private final ProgamerValorantRepository progamerValorantRepository;

}
