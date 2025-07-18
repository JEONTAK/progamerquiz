package pq.progamerquiz.domain.valorant.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.quizzes.entity.IGotYou;
import pq.progamerquiz.domain.quizzes.repository.IGotYouRepository;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.service.ProgamerTeamValorantService;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.service.ProgamerValorantQueryService;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.entity.ValorantIGotYouQuizProgamer;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.repository.ValorantIGotYouQuizProgamerRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantSimpleInfoResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

//Quiz : I Got You!
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ValorantIGotYouService {

    private final ProgamerValorantQueryService progamerValorantQueryService;
    private final ProgamerTeamValorantService progamerTeamValorantService;
    private final IGotYouRepository iGotYouRepository;
    private final ValorantIGotYouQuizProgamerRepository valorantIgotYouQuizProgamerRepository;

    public List<ValorantIGotYouQuizResponse> setQuizLists(Integer totalCount, Game game) {
        IGotYou iGotYou = IGotYou.create(totalCount, 0, game);
        IGotYou savedIGotYou = iGotYouRepository.save(iGotYou);
        List<ProgamerValorant> progamerList = progamerValorantQueryService.findRandomPlayers(totalCount);
        return LongStream.range(0, progamerList.size())
                .mapToObj(i -> {
                    ProgamerValorant progamer = progamerList.get((int) i);
                    valorantIgotYouQuizProgamerRepository.save(ValorantIGotYouQuizProgamer.create(savedIGotYou, progamer));
                    List<TeamValorantSimpleInfoResponse> teams = progamerTeamValorantService.findTeamsByProgamerId(progamer.getId());
                    return ValorantIGotYouQuizResponse.of(savedIGotYou.getId(), i + 1, progamer.getId(), progamer.getProgamerTag(), progamer.getNationality(), teams);
                })
                .collect(Collectors.toList());
    }

    public ValorantIGotYouResponse setQuiz(Long id, List<ValorantIGotYouQuizResponse> quizList) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return ValorantIGotYouResponse.of(iGotYou.getId(), 0, iGotYou.getTotalQuizCount(), iGotYou.getCorrectQuizCount(), quizList);
    }

    public ValorantIGotYouSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<ValorantIGotYouQuizProgamer> quizList = valorantIgotYouQuizProgamerRepository.findByIgotyouIdWithProgamer(id);
        ProgamerValorant submitProgamer = progamerValorantQueryService.findByProgamerTag(input);
        if(submitProgamer.getProgamerTag().equalsIgnoreCase(quizList.get(index).getAnswerProgamer().getProgamerTag())) {
            correctQuizCount++;
        }

        return ValorantIGotYouSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public ValorantIGotYouResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        IGotYou iGotYou = iGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        iGotYouRepository.updateCorrectQuizCount(id, correctQuizCount);
        return ValorantIGotYouResultResponse.of(iGotYou.getId(), correctQuizCount, totalQuizCount);
    }

}
