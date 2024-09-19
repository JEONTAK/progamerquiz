package pq.progamerquiz.team;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;


    public void saveTeam(Team team) {
        teamRepository.save(team);
    }
}
