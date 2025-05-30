package pq.progamerquiz.domain.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.service.ProgamerQueryService;
import pq.progamerquiz.domain.progamerteam.service.ProgamerTeamService;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouQuizResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouResultResponse;
import pq.progamerquiz.domain.quizzes.igotyou.dto.response.IGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.quizzes.igotyou.entity.IGotYou;
import pq.progamerquiz.domain.quizzes.igotyou.entity.IGotYouProgamer;
import pq.progamerquiz.domain.quizzes.igotyou.repository.IGotYouProgamerRepository;
import pq.progamerquiz.domain.quizzes.igotyou.repository.IGotYouRepository;
import pq.progamerquiz.domain.team.dto.response.TeamSimpleInfoResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

//Quiz : I Got You!
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IGotYouService {

    private final ProgamerQueryService progamerQueryService;
    private final ProgamerTeamService progamerTeamService;
    private final IGotYouRepository iGotYouRepository;
    private final IGotYouProgamerRepository igotYouProgamerRepository;

    public List<IGotYouQuizResponse> setQuizLists(int totalCount) {
        IGotYou iGotYou = IGotYou.create(totalCount, 0);
        IGotYou savedIGotYou = iGotYouRepository.save(iGotYou);
        List<Progamer> progamerList = progamerQueryService.findRandomPlayers(totalCount);
        return LongStream.range(0, progamerList.size())
                .mapToObj(i -> {
                    Progamer progamer = progamerList.get((int) i);
                    igotYouProgamerRepository.save(IGotYouProgamer.create(savedIGotYou, progamer));
                    List<TeamSimpleInfoResponse> teams = progamerTeamService.findTeamsByProgamerId(progamer.getId());
                    return IGotYouQuizResponse.of(savedIGotYou.getId(), i + 1, progamer.getId(), progamer.getProgamerTag(), progamer.getPosition(), teams);
                })
                .collect(Collectors.toList());
    }

    public IGotYouResponse setQuiz(Long id, List<IGotYouQuizResponse> quizList) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return IGotYouResponse.of(iGotYou.getId(), 0, iGotYou.getTotalQuizCount(), iGotYou.getCorrectQuizCount(), quizList);
    }

    public IGotYouSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<IGotYouProgamer> quizList = igotYouProgamerRepository.findByIgotyouIdWithProgamer(id);
        Progamer submitProgamer = progamerQueryService.findByProgamerTag(input);
        if(submitProgamer.getProgamerTag().equalsIgnoreCase(quizList.get(index).getAnswerProgamer().getProgamerTag())) {
            correctQuizCount++;
        }

        return IGotYouSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public IGotYouResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        iGotYouRepository.updateCorrectQuizCount(id, correctQuizCount);
        return IGotYouResultResponse.of(iGotYou.getId(), correctQuizCount, totalQuizCount);
    }
}
