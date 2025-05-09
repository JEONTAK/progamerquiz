package pq.progamerquiz.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.team.repository.TeamRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
}
