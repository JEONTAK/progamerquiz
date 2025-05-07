package pq.progamerquiz.domain.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.whoareyou.dto.WheAreYouDto;
import pq.progamerquiz.domain.progamer.service.ProgamerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class WheAreYouService {

    final private ProgamerService progamerService;

    public Optional<ProgamerDto> findByPid(String pid){
        return progamerService.findByPid(pid);
    }

    public WheAreYouDto getRandomProgamer() {
        List<ProgamerDto> progamer = progamerService.findRandomPlayers(1);
        return progamer.stream()
                .findFirst()
                .map(progamerDto -> WheAreYouDto.convert(Optional.of(progamerDto)))
                .orElseThrow(() -> new IllegalArgumentException("No progamer found"));
    }

    public String getImagePath(WheAreYouDto answer) {
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
}
