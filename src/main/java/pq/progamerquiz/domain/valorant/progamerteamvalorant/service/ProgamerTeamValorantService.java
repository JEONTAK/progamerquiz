package pq.progamerquiz.domain.valorant.progamerteamvalorant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.repository.ProgamerTeamValorantRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgamerTeamValorantService {

    private final ProgamerTeamValorantRepository progamerTeamValorantRepository;


    public TeamValorant findRecentTeamByProgamer(Long progamerId) {

        return null;
    }

    public List<TeamSimpleInfoResponse> findTeamsByProgamerId(Long progamerId) {
        return progamerTeamValorantRepository.findTeamsByProgamerId(progamerId);
    }

    public List<ProgamerSimpleInfoResponse> findProgamersByTeamId(Long teamId) {
        return progamerTeamValorantRepository.findProgamersByTeamId(teamId);
    }

    public List<Long> findTeamIdsWithFiveOrMoreProgamers() {
        return progamerTeamValorantRepository.findTeamIdsWithFiveOrMoreProgamers();
    }
}
