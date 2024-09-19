package pq.progamerquiz.quiz.q1_whoareyou;


import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
public class Quiz1Service {

    @Autowired
    private ProgamerRepository progamerRepository;

    public Progamer getRandomProgamer() {
        List<Progamer> progamerList = progamerRepository.findAll();
        Random random = new Random();
        Progamer answer = progamerList.get(random.nextInt(progamerList.size()));
        log.info(answer.getId());
        log.info(answer.getPid());
        return answer;
    }

    public String getImagePath(Progamer answer) {
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
        Optional<Progamer> result = progamerRepository.findById(pid);
        return result;
    }
}
