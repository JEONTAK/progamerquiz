package pq.progamerquiz.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public List<Team> findAll() {
       return teamRepository.findAll();
    }

    public Team find(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public List<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    public Long findIdByName(String teamName) {
        return teamRepository.findNameById(teamName);
    }

    public List<TeamDto> findRandomTeams(int totalCount, String league) {
        List<Team> list = teamRepository.findRandomTeams(totalCount, league);
        return list.stream()
                .map(team -> new TeamDto(team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),team.getLeague().toString()
                        ,team.getSpring_rank(), team.getSummer_rank(), team.getMsi_rank(), team.getWorlds_rank(), team.getWinter_rank()
                        ,team.getRoster(), team.getImage_path()))
                .collect(Collectors.toList());
    }

    public List<Team> findByNameOrCallName(String teamName) {
        return teamRepository.findByNameOrCallName(teamName);
    }
}
