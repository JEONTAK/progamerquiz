package pq.progamerquiz.domain.valorant.progamervalorant.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.repository.ProgamerValorantRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgamerValorantQueryService {

    private final ProgamerValorantRepository progamerValorantRepository;

    public ProgamerValorant findRandomProgamer() {
        return progamerValorantRepository.findRandomProgamer();
    }

    public ProgamerValorant findByProgamerTag(String progamerTag) {
        return progamerValorantRepository.findByProgamerTag(progamerTag).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 프로게이머를 찾을 수 없습니다."));
    }


    public List<ProgamerValorant> findRandomPlayers(int totalCount) {
        return IntStream.range(0, totalCount).mapToObj(i -> progamerValorantRepository.findRandomProgamer()).collect(Collectors.toList());
    }

    public ProgamerValorant findOneByIds(List<Long> rosterIds) {
        return progamerValorantRepository.findOneRandomProgamer(rosterIds).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "프로게이머를 찾을 수 없습니다."));
    }
}
