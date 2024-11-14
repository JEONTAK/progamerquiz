package pq.progamerquiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.dto.ProgamerDto;
import org.springframework.stereotype.Service;
import pq.progamerquiz.dto.Quiz1Dto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class Quiz1Service {

    final private ProgamerService progamerService;

    public Quiz1Dto getRandomProgamer() {
        List<ProgamerDto> progamer = progamerService.findRandomPlayers(1);
        Quiz1Dto quiz1Dto = Quiz1Dto.convert(progamer.get(0));
        log.info("ID: {}, PID: {}", quiz1Dto.getId(), quiz1Dto.getPid());
        return quiz1Dto;
    }

    public String getImagePath(Quiz1Dto answer) {
        String imagePath = "/images/player/" + answer.getId() + ".webp";
        try{
            Path path = Paths.get(new ClassPathResource("static" + imagePath).getURI());
            if (!Files.exists(path)) {
                log.warn("Image not found for ID: {}, using default image.", answer.getId());
                return "/images/none.png";
            }
        } catch (IOException e) {
            log.error("Failed to load image for ID: {}, error: {}", answer.getId(), e.getMessage());
            return "/images/none.png";
        }
        return imagePath;
    }

    public ProgamerDto findByPid(String pid){
        return progamerService.findByPid(pid);
    }
}
