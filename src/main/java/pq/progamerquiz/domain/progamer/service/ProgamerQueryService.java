package pq.progamerquiz.domain.progamer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.repository.ProgamerRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgamerQueryService {

    private final ProgamerRepository progamerRepository;

    public Progamer findRandomProgamer() {
        return progamerRepository.findRandomProgamer();
    }

    public Progamer findByProgamerTag(String progamerTag) {
        return progamerRepository.findByProgamerTag(progamerTag).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 프로게이머를 찾을 수 없습니다."));
    }


    public List<Progamer> findRandomPlayers(int totalCount) {
        return IntStream.range(0, totalCount).mapToObj(i -> progamerRepository.findRandomProgamer()).collect(Collectors.toList());
    }
}
