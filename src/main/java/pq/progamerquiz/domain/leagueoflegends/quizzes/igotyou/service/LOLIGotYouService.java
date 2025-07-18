package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.service.ProgamerLOLQueryService;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response.LOLIGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.LOLIGotYouQuizProgamer;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository.LOLIGotYouQuizProgamerRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamLOLSimpleInfoResponse;
import pq.progamerquiz.domain.quizzes.entity.IGotYou;
import pq.progamerquiz.domain.quizzes.repository.IGotYouRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

//Quiz : I Got You!
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LOLIGotYouService {

    private final ProgamerLOLQueryService progamerLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;
    private final IGotYouRepository iGotYouRepository;
    private final LOLIGotYouQuizProgamerRepository igotYouQuizProgamerRepository;

    public List<LOLIGotYouQuizResponse> setQuizLists(int totalCount, Game game) {
        IGotYou iGotYou = IGotYou.create(totalCount, 0, game);
        IGotYou savedIGotYou = iGotYouRepository.save(iGotYou);
        List<ProgamerLOL> progamerLOLList = progamerLOLQueryService.findRandomPlayers(totalCount);
        return LongStream.range(0, progamerLOLList.size())
                .mapToObj(i -> {
                    ProgamerLOL progamerLOL = progamerLOLList.get((int) i);
                    igotYouQuizProgamerRepository.save(LOLIGotYouQuizProgamer.create(savedIGotYou, progamerLOL));
                    List<TeamLOLSimpleInfoResponse> teams = progamerTeamLOLService.findTeamsByProgamerId(progamerLOL.getId());
                    return LOLIGotYouQuizResponse.of(savedIGotYou.getId(), i + 1, progamerLOL.getId(), progamerLOL.getProgamerTag(), progamerLOL.getPosition(), teams);
                })
                .collect(Collectors.toList());
    }

    public LOLIGotYouResponse setQuiz(Long id, List<LOLIGotYouQuizResponse> quizList) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return LOLIGotYouResponse.of(iGotYou.getId(), 0, iGotYou.getTotalQuizCount(), iGotYou.getCorrectQuizCount(), quizList);
    }

    public LOLIGotYouSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<LOLIGotYouQuizProgamer> quizList = igotYouQuizProgamerRepository.findByIgotyouIdWithProgamer(id);
        ProgamerLOL submitProgamerLOL = progamerLOLQueryService.findByProgamerTag(input);
        if(submitProgamerLOL.getProgamerTag().equalsIgnoreCase(quizList.get(index).getAnswerProgamerLOL().getProgamerTag())) {
            correctQuizCount++;
        }

        return LOLIGotYouSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public LOLIGotYouResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        iGotYouRepository.updateCorrectQuizCount(id, correctQuizCount);
        return LOLIGotYouResultResponse.of(iGotYou.getId(), correctQuizCount, totalQuizCount);
    }
}
