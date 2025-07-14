package pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.repository.ProgamerTeamLOLRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgamerTeamLOLService {

    private final ProgamerTeamLOLRepository progamerTeamLOLRepository;


    public TeamLOL findRecentTeamByProgamer(Long progamerId) {

        return null;
    }

    public List<TeamSimpleInfoResponse> findTeamsByProgamerId(Long progamerId) {
        return progamerTeamLOLRepository.findTeamsByProgamerId(progamerId);
    }

    public List<ProgamerSimpleInfoResponse> findProgamersByTeamId(Long teamId) {
        return progamerTeamLOLRepository.findProgamersByTeamId(teamId);
    }

    public List<Long> findTeamIdsWithFiveOrMoreProgamers() {
        return progamerTeamLOLRepository.findTeamIdsWithFiveOrMoreProgamers();
    }
}
