package pq.progamerquiz.domain.leagueoflegends.progamerlol.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.repository.ProgamerLOLRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgamerLOLQueryService {

    private final ProgamerLOLRepository progamerLOLRepository;

    public ProgamerLOL findRandomProgamer() {
        return progamerLOLRepository.findRandomProgamer();
    }

    public ProgamerLOL findByProgamerTag(String progamerTag) {
        return progamerLOLRepository.findByProgamerTag(progamerTag).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 프로게이머를 찾을 수 없습니다."));
    }


    public List<ProgamerLOL> findRandomPlayers(int totalCount) {
        return IntStream.range(0, totalCount).mapToObj(i -> progamerLOLRepository.findRandomProgamer()).collect(Collectors.toList());
    }

    public ProgamerLOL findOneByIds(List<Long> rosterIds) {
        return progamerLOLRepository.findOneRandomProgamer(rosterIds).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "프로게이머를 찾을 수 없습니다."));
    }
}
