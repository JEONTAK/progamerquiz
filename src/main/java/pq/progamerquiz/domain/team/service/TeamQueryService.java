package pq.progamerquiz.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamerteam.repository.ProgamerTeamRepository;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public List<Team> findRandomTeams(Integer totalQuizCount) {
        return IntStream.range(0, totalQuizCount).mapToObj(i -> teamRepository.findRandomTeam()).collect(Collectors.toList());
    }
}
