package pq.progamerquiz.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz2Dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Quiz : I Got you!
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class Quiz2Service {

    final private ProgamerService progamerService;

    public List<Quiz2Dto> getProgamers(int totalCount) {
        List<ProgamerDto> progamerList = progamerService.findRandomPlayers(totalCount);
        return IntStream.range(0, progamerList.size())
                .mapToObj(i -> Quiz2Dto.convert(i + 1, progamerList.get(i)))  // i + 1은 인덱스 1부터 시작하기 위함
                .collect(Collectors.toList());
    }

    public boolean isExist(String userInput) {
        return progamerService.findByPid(userInput) != null;
    }

    public boolean isAnswer(String userInput, Quiz2Dto quiz2Dto) {
        return Objects.equals(progamerService.findByPid(userInput).getId(), quiz2Dto.getId());
    }
}
