package pq.progamerquiz.quiz.q1_whoareyou;


import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pq.progamerquiz.progamer.ProgamerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
public class Quiz1Service {

    @Autowired
    private ProgamerService progamerService;

    public Quiz1Dto getRandomProgamer() {
        List<ProgamerDto> progamer = progamerService.findRandomPlayers(1);
        Quiz1Dto quiz1Dto = convert(progamer.get(0));
        log.info(quiz1Dto.getId());
        log.info(quiz1Dto.getPid());
        return quiz1Dto;
    }

    public String getImagePath(Quiz1Dto answer) {
        String imagePath = "/images/player/" + answer.getId() + ".webp";
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

    public ProgamerDto findByPid(String pid){
        return progamerService.findByPid(pid);
    }

    public static Quiz1Dto convert(ProgamerDto submitProgamer) {
        Quiz1Dto result = new Quiz1Dto(
                submitProgamer.getId(),
                submitProgamer.getPid(),
                submitProgamer.getName(),
                submitProgamer.getBirth(),
                submitProgamer.getPosition().toString(),
                submitProgamer.getLeague_win(),
                submitProgamer.getIntl_win(),
                submitProgamer.getNationality(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getName(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getImage_path(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getLeague().toString()
        );
        return result;
    }

}
