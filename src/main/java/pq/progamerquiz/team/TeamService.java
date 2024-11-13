package pq.progamerquiz.team;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamService {

    private TeamRepository teamRepository;

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }


    public Team find(Long id) {
        return teamRepository.findById(id).orElse(null);
    }


    public Long findIdByName(String teamName) {
        return teamRepository.findIdByName(teamName);
    }

    public List<TeamDto> findRandomTeams(int totalCount, String league) {
        return teamRepository.findRandomTeams(totalCount, league)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Team> findByNameOrCallName(String teamName) {
        return teamRepository.findByNameOrCallName(teamName);
    }

    private TeamDto toDto(Team team) {
        return new TeamDto(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague().toString(), team.getSpring_rank(), team.getSummer_rank(),
                team.getMsi_rank(), team.getWorlds_rank(), team.getWinter_rank(),
                team.getRoster(), team.getImage_path()
        );
    }
}
