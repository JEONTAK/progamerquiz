package pq.progamerquiz.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.Progamer;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.repository.ProgamerRepository;

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
        return ProgamerDto.toDto(progamerRepository.findByPidIgnoreCase(pid));
    }

    public void saveProgamer(Progamer progamer) {
        progamerRepository.save(progamer);
    }

    public List<ProgamerDto> findRandomPlayers(int totalCount) {
        return progamerRepository.findRandomPlayers(totalCount)
                .stream()
                .map(ProgamerDto::toDto)
                .collect(Collectors.toList());
    }
}
