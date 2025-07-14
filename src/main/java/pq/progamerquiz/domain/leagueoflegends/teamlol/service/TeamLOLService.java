package pq.progamerquiz.domain.leagueoflegends.teamlol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.leagueoflegends.teamlol.repository.TeamLOLRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamLOLService {

    private final TeamLOLRepository teamLOLRepository;
}
