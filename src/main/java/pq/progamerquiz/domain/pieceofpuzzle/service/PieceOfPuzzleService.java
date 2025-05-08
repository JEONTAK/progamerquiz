package pq.progamerquiz.domain.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.pieceofpuzzle.dto.response.PieceOfPuzzleResponse;
import pq.progamerquiz.domain.team.dto.TeamDto;
import pq.progamerquiz.domain.progamer.service.ProgamerService;
import pq.progamerquiz.domain.team.service.TeamService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


//Quiz : Piece Of Puzzle
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class PieceOfPuzzleService {

    final private TeamService teamService;
    final private ProgamerService progamerService;

    public Optional<ProgamerDto> findByPid(String pid){
        return progamerService.findByPid(pid);
    }

    public List<PieceOfPuzzleResponse> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findTeamsWithRosterSize(totalCount, league);
        AtomicInteger quizIdx = new AtomicInteger(1);
        return teamList
                .stream()
                .map(team -> convert(quizIdx.getAndIncrement(), team))
                .toList();
    }

    static List<Map<Long, Boolean>> getTwoRandomProgamers(List<ProgamerDto> roster) {
        if(roster.size() < 5) {
            return null;
        }
        List<Map<Long, Boolean>> answer = new ArrayList<>();
        List<ProgamerDto> mutableRoster = new ArrayList<>(roster);
        Collections.shuffle(mutableRoster);
        for (ProgamerDto progamerDto : mutableRoster.subList(0, 2)) {
            Map<Long, Boolean> progamerMap = new HashMap<>();
            progamerMap.put(progamerDto.getId(), false);  // id를 키로 하고 초기 상태는 false
            answer.add(progamerMap);
        }
        return answer;
    }

    public boolean isAnswer(Optional<ProgamerDto> input, PieceOfPuzzleResponse pieceOfPuzzleResponse) {
        for (Map<Long, Boolean> answer : pieceOfPuzzleResponse.getAnswer()) {
            for (Map.Entry<Long, Boolean> entry : answer.entrySet()) {
                if (input.get().getId().equals(entry.getKey()) && !entry.getValue()) {
                    entry.setValue(true);
                    return true;
                }
            }
        }
        return false;
    }

    public PieceOfPuzzleResponse convert(int idx, TeamDto submitTeam) {
        List<ProgamerDto> roster = submitTeam.getRoster();
        List<Map<Long, Boolean>> answer = getTwoRandomProgamers(roster);
        if (answer == null) {
            return null;
        }

        return new PieceOfPuzzleResponse(
                (long) idx - 1,
                submitTeam.getId(),
                submitTeam.getName(),
                submitTeam.getSeasonYear(),
                roster,
                answer,
                submitTeam.getImage_path(),
                0,
                0
        );
    }

}
