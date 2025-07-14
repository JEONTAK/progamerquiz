package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.service.ProgamerLOLQueryService;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.IGotYou;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.IGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.IGotYouQuizProgamer;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository.IGotYouQuizProgamerRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository.IGotYouRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamSimpleInfoResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

//Quiz : I Got You!
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IGotYouService {

    private final ProgamerLOLQueryService progamerLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;
    private final IGotYouRepository iGotYouRepository;
    private final IGotYouQuizProgamerRepository igotYouQuizProgamerRepository;

    public List<IGotYouQuizResponse> setQuizLists(int totalCount) {
        IGotYou iGotYou = IGotYou.create(totalCount, 0);
        IGotYou savedIGotYou = iGotYouRepository.save(iGotYou);
        List<ProgamerLOL> progamerLOLList = progamerLOLQueryService.findRandomPlayers(totalCount);
        return LongStream.range(0, progamerLOLList.size())
                .mapToObj(i -> {
                    ProgamerLOL progamerLOL = progamerLOLList.get((int) i);
                    igotYouQuizProgamerRepository.save(IGotYouQuizProgamer.create(savedIGotYou, progamerLOL));
                    List<TeamSimpleInfoResponse> teams = progamerTeamLOLService.findTeamsByProgamerId(progamerLOL.getId());
                    return IGotYouQuizResponse.of(savedIGotYou.getId(), i + 1, progamerLOL.getId(), progamerLOL.getProgamerTag(), progamerLOL.getPosition(), teams);
                })
                .collect(Collectors.toList());
    }

    public IGotYouResponse setQuiz(Long id, List<IGotYouQuizResponse> quizList) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return IGotYouResponse.of(iGotYou.getId(), 0, iGotYou.getTotalQuizCount(), iGotYou.getCorrectQuizCount(), quizList);
    }

    public IGotYouSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<IGotYouQuizProgamer> quizList = igotYouQuizProgamerRepository.findByIgotyouIdWithProgamer(id);
        ProgamerLOL submitProgamerLOL = progamerLOLQueryService.findByProgamerTag(input);
        if(submitProgamerLOL.getProgamerTag().equalsIgnoreCase(quizList.get(index).getAnswerProgamerLOL().getProgamerTag())) {
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
