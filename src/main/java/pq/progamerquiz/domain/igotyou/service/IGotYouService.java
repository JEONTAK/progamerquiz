package pq.progamerquiz.domain.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.igotyou.dto.IGotYouDto;
import pq.progamerquiz.domain.progamer.service.ProgamerService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Quiz : I Got You!
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class IGotYouService {

    final private ProgamerService progamerService;

    public List<IGotYouDto> getProgamers(int totalCount) {
        List<ProgamerDto> progamerList = progamerService.findRandomPlayers(totalCount);
        return IntStream.range(0, progamerList.size())
                .mapToObj(i -> IGotYouDto.convert(i + 1, progamerList.get(i)))  // i + 1은 인덱스 1부터 시작하기 위함
                .collect(Collectors.toList());
    }

    public boolean isAnswer(Optional<ProgamerDto> progamerDto, IGotYouDto IGotYouDto) {
        return Objects.equals(progamerDto.get().getId(), IGotYouDto.getId());
    }

    public Optional<ProgamerDto> findByPid(String pid){
        return progamerService.findByPid(pid);
    }
}
