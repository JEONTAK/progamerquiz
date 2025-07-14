package pq.progamerquiz.domain.valorant.teamvalorant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.valorant.teamvalorant.repository.TeamValorantRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamValorantService {

    private final TeamValorantRepository teamValorantRepository;
}
