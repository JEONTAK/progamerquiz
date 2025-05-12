package pq.progamerquiz.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamerteam.repository.ProgamerTeamRepository;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.repository.TeamRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final TeamRepository teamRepository;
    private final ProgamerTeamRepository progamerTeamRepository;


    public Team findRecentTeamByProgamer(Long progamerId) {
        return teamRepository.findLatestTeamByProgamerId(progamerId);
    }

    public Team findRecentTeamByProgamerTag(String progamerTag) {
        return teamRepository.findLatestTeamByProgamerTag(progamerTag);
    }
}
