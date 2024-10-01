package pq.progamerquiz.team;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    public Long findIdByName(String teamName) {
        return teamRepository.findNameById(teamName);
    }
}
