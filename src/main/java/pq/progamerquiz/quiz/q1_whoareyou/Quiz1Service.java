package pq.progamerquiz.quiz.q1_whoareyou;


import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Hibernate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pq.progamerquiz.team.Team;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@Transactional
public class Quiz1Service {

    @Autowired
    private ProgamerRepository progamerRepository;

    @Autowired
    private EntityManager em;

    public ProgamerDto getRandomProgamer() {
        List<Progamer> progamerList = progamerRepository.findAll();
        Random random = new Random();
        Progamer answer = progamerList.get(random.nextInt(progamerList.size()));
        Hibernate.initialize(answer.getTeams());
        ProgamerDto progamerDto = new ProgamerDto(
                answer.getId(),
                answer.getPid(),
                answer.getName(),
                answer.getBirth(),
                answer.getPosition().toString(),
                answer.getLeague_win(),
                answer.getIntl_win(),
                answer.getNationality(),
                answer.getLatestTeam(),
                answer.getLatestLeague()
        );
        log.info(answer.getId());
        log.info(answer.getPid());
        return progamerDto;
    }

    public String getImagePath(ProgamerDto answer) {
        String imagePath = "/images/player/" + answer.getId() + ".png";
        try{
            Path path = Paths.get(new ClassPathResource("static" + imagePath).getURI());
            if (!Files.exists(path)) {
                imagePath = "/images/none.png";
            }
        } catch (IOException e) {
            imagePath = "/images/none.png";
        }

        return imagePath;
    }

    public Optional<Progamer> findById(Long pid){
        return progamerRepository.findById(pid);
    }

    public Progamer findByPid(String pid){
        return progamerRepository.findByPid(pid);
    }

    public ProgamerDto findProgamerDtoById(Long progamerId) {
        Optional<Progamer> result = progamerRepository.findById(progamerId);
        if (result.isPresent()) {
            Progamer progamer = result.get();

            // Progamer를 DTO로 변환
            ProgamerDto progamerDto = new ProgamerDto();
            progamerDto.setId(progamer.getId());
            progamerDto.setPid(progamer.getPid());
            progamerDto.setName(progamer.getName());
            progamerDto.setBirth(progamer.getBirth());
            progamerDto.setPosition(progamer.getPosition().toString());
            progamerDto.setLeague_win(progamer.getLeague_win());
            progamerDto.setIntl_win(progamer.getIntl_win());
            progamerDto.setNationality(progamer.getNationality());

            // teams에서 가장 최신 팀 가져오기
            List<Team> teams = progamer.getTeams();
            if (!teams.isEmpty()) {
                Team latestTeam = teams.get(teams.size() - 1); // 최신 팀 가져오기
                progamerDto.setLatestTeam(latestTeam.getId());
                progamerDto.setLatestLeague(String.valueOf(latestTeam.getLeague()));
            }

            return progamerDto;
        }
        return null;
    }
}
