package pq.progamerquiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.Team;
import pq.progamerquiz.dto.TeamDto;
import pq.progamerquiz.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamService {

    final private TeamRepository teamRepository;
    /*private Map<Long, TeamDto> teamCache;


    @PostConstruct
    public void init() {
        teamCache = teamRepository.findAll().stream()
                .collect(Collectors.toMap(Team::getId, this::toDto));
    }*/


    public void saveTeam(Team team) {
        teamRepository.save(team);
    }


    public TeamDto find(Long id) {
        return teamRepository.findById(id)
                .map(TeamDto::toDto)
                .orElse(null);
    }

    public Long findIdByName(String teamName) {
        return teamRepository.findIdByName(teamName);
    }

    public List<TeamDto> findRandomTeams(int totalCount, String league) {
        return teamRepository.findRandomTeams(totalCount, league)
                .stream()
                .map(TeamDto::toDto)
                .collect(Collectors.toList());
    }


    public List<TeamDto> findByNameOrCallName(String teamName) {
        return teamRepository.findByNameOrCallName(teamName)
                .stream()
                .map(TeamDto::toDto)
                .toList();
    }
}
