package pq.progamerquiz.progamer;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerService {

    private final ProgamerRepository progamerRepository;

    public Progamer findOne(Long progamerId) {
        return progamerRepository.findById(progamerId).orElse(null);
    }

    public ProgamerDto findByPid(String pid) {
        return toDto(progamerRepository.findByPidIgnoreCase(pid));
    }

    public void save(Progamer progamer) {
        progamerRepository.save(progamer);
    }

    public List<ProgamerDto> findRandomPlayers(int totalCount) {
        return progamerRepository.findRandomPlayers(totalCount)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProgamerDto toDto(Progamer progamer) {
        return new ProgamerDto(
                progamer.getId(), progamer.getPid(), progamer.getName(), progamer.getBirth(),
                progamer.getPosition().toString(), progamer.getLeague_win(),
                progamer.getIntl_win(), progamer.getNationality(), progamer.getTeams()
        );
    }
}
