package pq.progamerquiz.domain.leagueoflegends.teamlol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.repository.ProgamerTeamLOLRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.repository.TeamLOLRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamLOLQueryService {

    private final TeamLOLRepository teamLOLRepository;
    private final ProgamerTeamLOLRepository progamerTeamLOLRepository;


    public TeamLOL findRecentTeamByProgamer(Long progamerId) {
        return teamLOLRepository.findLatestTeamByProgamerId(progamerId);
    }

    public TeamLOL findRecentTeamByProgamerTag(String progamerTag) {
        return teamLOLRepository.findLatestTeamByProgamerTag(progamerTag);
    }

    public List<TeamLOL> findRandomTeams(Integer totalQuizCount, List<Long> teamIds) {
        return teamLOLRepository.findRandomTeam(teamIds, totalQuizCount);
    }

    public List<TeamLOL> findByTeamName(String input) {
        return teamLOLRepository.findAllByName(input);
    }
}
