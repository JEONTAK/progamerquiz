package pq.progamerquiz.domain.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerInsertResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.service.ProgamerCommandService;
import pq.progamerquiz.domain.progamer.service.ProgamerQueryService;
import pq.progamerquiz.domain.progamerteam.service.ProgamerTeamService;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

//Quiz : I Got You!
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IGotYouService {

    private final ProgamerQueryService progamerQueryService;
    private final ProgamerCommandService progamerCommandService;
    private final ProgamerTeamService progamerTeamService;

    public List<IGotYouResponse> setQuizLists(int totalCount) {
        List<Progamer> progamerList = progamerQueryService.findRandomPlayers(totalCount);
        return LongStream.range(0, progamerList.size())
                .mapToObj(i -> {
                    Progamer progamer = progamerList.get((int) i);
                    List<TeamSimpleInfoResponse> teams = progamerTeamService.findTeamsByProgamerId(progamer.getId());
                    return IGotYouResponse.of(i + 1, progamer.getId(), progamer.getProgamerTag(), progamer.getPosition(), teams);
                })
                .collect(Collectors.toList());
    }

    public boolean isAnswer(Optional<ProgamerInsertResponse> progamerDto, IGotYouResponse IGotYouResponse) {
        return Objects.equals(progamerDto.get().getId(), IGotYouResponse.getId());
    }

    public Progamer findByPid(String progamerTag){
        return progamerQueryService.findByProgamerTag(progamerTag);
    }
}
