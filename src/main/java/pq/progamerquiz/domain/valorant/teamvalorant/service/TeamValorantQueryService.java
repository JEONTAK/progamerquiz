package pq.progamerquiz.domain.valorant.teamvalorant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.repository.ProgamerTeamValorantRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.repository.TeamValorantRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamValorantQueryService {

    private final TeamValorantRepository teamValorantRepository;
    private final ProgamerTeamValorantRepository progamerTeamValorantRepository;


    public TeamValorant findRecentTeamByProgamer(Long progamerId) {
        return teamValorantRepository.findLatestTeamByProgamerId(progamerId);
    }

    public TeamValorant findRecentTeamByProgamerTag(String progamerTag) {
        return teamValorantRepository.findLatestTeamByProgamerTag(progamerTag);
    }

    public List<TeamValorant> findRandomTeams(Integer totalQuizCount, List<Long> teamIds) {
        return teamValorantRepository.findRandomTeam(teamIds, totalQuizCount);
    }

    public List<TeamValorant> findByTeamName(String input) {
        return teamValorantRepository.findAllByName(input);
    }
}
