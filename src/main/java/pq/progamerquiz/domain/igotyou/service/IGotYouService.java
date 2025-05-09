package pq.progamerquiz.domain.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Quiz : I Got You!
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class IGotYouService {

  /*  private final ProgamerService progamerService;*/

    /*public List<IGotYouResponse> getProgamers(int totalCount) {
        List<ProgamerInsertResponse> progamerList = progamerService.findRandomPlayers(totalCount);
        return IntStream.range(0, progamerList.size())
                .mapToObj(i -> IGotYouResponse.of(i + 1, progamerList.get(i)))  // i + 1은 인덱스 1부터 시작하기 위함
                .collect(Collectors.toList());
    }

    public boolean isAnswer(Optional<ProgamerInsertResponse> progamerDto, IGotYouResponse IGotYouResponse) {
        return Objects.equals(progamerDto.get().getId(), IGotYouResponse.getId());
    }

    public Optional<ProgamerInsertResponse> findByPid(String pid){
        return progamerService.findByPid(pid);
    }*/
}
