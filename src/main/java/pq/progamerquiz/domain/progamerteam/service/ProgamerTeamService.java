package pq.progamerquiz.domain.progamerteam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.progamerteam.repository.ProgamerTeamRepository;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;
import pq.progamerquiz.domain.team.entity.Team;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgamerTeamService {

    private final ProgamerTeamRepository progamerTeamRepository;


    public Team findRecentTeamByProgamer(Long progamerId) {

        return null;
    }

    public List<TeamSimpleInfoResponse> findTeamsByProgamerId(Long progamerId) {
        return progamerTeamRepository.findTeamsByProgamerId(progamerId);
    }

    public List<ProgamerSimpleInfoResponse> findProgamersByTeamId(Long teamId) {
        return progamerTeamRepository.findProgamersByTeamId(teamId);
    }
}
