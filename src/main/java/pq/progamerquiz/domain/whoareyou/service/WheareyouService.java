package pq.progamerquiz.domain.whoareyou.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.service.ProgamerService;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class WheareyouService {

    final private ProgamerService progamerService;
    private static final int MAX_ATTEMPTS = 8;

    /*public Optional<ProgamerInsertResponse> findByPid(String pid){
        return progamerService.findByPid(pid);
    }

    public WhoareyouResponse getRandomProgamer() {
        List<ProgamerInsertResponse> progamer = progamerService.findRandomPlayers(1);
        return progamer.stream()
                .findFirst()
                .map(progamerDto -> WhoareyouResponse.of(Optional.of(progamerDto)))
                .orElseThrow(() -> new IllegalArgumentException("No progamer found"));
    }

    public String getImagePath(WhoareyouResponse answer) {
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
    }*/
}
