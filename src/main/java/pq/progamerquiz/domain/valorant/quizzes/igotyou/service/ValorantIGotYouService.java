package pq.progamerquiz.domain.valorant.quizzes.igotyou.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.service.ProgamerTeamValorantService;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.service.ProgamerValorantQueryService;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response.ValorantIGotYouSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.entity.ValorantIGotYou;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.entity.ValorantIGotYouQuizProgamer;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.repository.ValorantIGotYouQuizProgamerRepository;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.repository.ValorantIGotYouRepository;
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
    private final ValorantIGotYouRepository valorantIGotYouRepository;
    private final ValorantIGotYouQuizProgamerRepository valorantIgotYouQuizProgamerRepository;

    public List<ValorantIGotYouQuizResponse> setQuizLists(int totalCount) {
        ValorantIGotYou iGotYou = ValorantIGotYou.create(totalCount, 0);
        ValorantIGotYou savedIGotYou = valorantIGotYouRepository.save(iGotYou);
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
        ValorantIGotYou iGotYou = valorantIGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
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
        ValorantIGotYou iGotYou = valorantIGotYouRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        valorantIGotYouRepository.updateCorrectQuizCount(id, correctQuizCount);
        return ValorantIGotYouResultResponse.of(iGotYou.getId(), correctQuizCount, totalQuizCount);
    }

}
