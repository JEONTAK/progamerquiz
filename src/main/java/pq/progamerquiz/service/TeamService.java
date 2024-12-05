package pq.progamerquiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public List<TeamDto> findRandomTeams(int totalCount, String league) {
        // 1단계: 랜덤 ID 추출
        Pageable pageable = PageRequest.of(0, totalCount); // LIMIT과 동일한 역할
        List<Long> randomIds = teamRepository.findRandomTeamIds(pageable, league);

        // 2단계: FETCH JOIN으로 연관 데이터 로드
        return teamRepository.findTeamsByIds(randomIds)
                .stream()
                .map(TeamDto::toDtoForNotUseRoster)
                .collect(Collectors.toList());
    }


    public List<TeamDto> findByNameOrCallName(String teamName) {
        return teamRepository.findByNameOrCallName(teamName)
                .stream()
                .map(TeamDto::toDtoForNotUseRoster)
                .toList();
    }
}
