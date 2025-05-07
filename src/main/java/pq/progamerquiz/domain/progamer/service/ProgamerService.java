package pq.progamerquiz.domain.progamer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.progamer.repository.ProgamerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgamerService {

    private final ProgamerRepository progamerRepository;

    public void saveProgamer(Progamer progamer) {
        progamerRepository.save(progamer);
    }

    public Progamer findOne(Long progamerId) {
        return progamerRepository.findById(progamerId).orElse(null);
    }

    public Optional<ProgamerDto> findByPid(String pid) {
        return Optional.ofNullable(ProgamerDto.toDto(progamerRepository.findByPidIgnoreCase(pid)));
    }

    public List<ProgamerDto> findRandomPlayers(int totalCount) {
        // 1단계: 랜덤 ID 추출
        Pageable pageable = PageRequest.of(0, totalCount); // LIMIT과 동일한 역할
        List<Long> randomIds = progamerRepository.findRandomProgamerIds(pageable);

        // 2단계: FETCH JOIN으로 연관 데이터 로드
        return progamerRepository.findProgamersWithTeamsByIds(randomIds)
                .stream()
                .map(ProgamerDto::toDto)
                .collect(Collectors.toList());
    }

    /*public List<ProgamerDto> findRandomPlayers(int totalCount) {
        return progamerRepository.findRandomPlayers(totalCount)
                .stream()
                .map(ProgamerDto::toDto)
                .collect(Collectors.toList());
    }*/
}
