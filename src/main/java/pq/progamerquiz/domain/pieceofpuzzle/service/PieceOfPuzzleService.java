package pq.progamerquiz.domain.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//Quiz : Piece Of Puzzle
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class PieceOfPuzzleService {
/*
    private final TeamService teamService;
    private final ProgamerService progamerService;*/

  /*  public Optional<ProgamerInsertResponse> findByPid(String pid){
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

    static List<Map<Long, Boolean>> getTwoRandomProgamers(List<ProgamerInsertResponse> roster) {
        if(roster.size() < 5) {
            return null;
        }
        List<Map<Long, Boolean>> answer = new ArrayList<>();
        List<ProgamerInsertResponse> mutableRoster = new ArrayList<>(roster);
        Collections.shuffle(mutableRoster);
        for (ProgamerInsertResponse progamerInsertResponse : mutableRoster.subList(0, 2)) {
            Map<Long, Boolean> progamerMap = new HashMap<>();
            progamerMap.put(progamerInsertResponse.getId(), false);  // id를 키로 하고 초기 상태는 false
            answer.add(progamerMap);
        }
        return answer;
    }

    public boolean isAnswer(Optional<ProgamerInsertResponse> input, PieceOfPuzzleResponse pieceOfPuzzleResponse) {
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
        List<ProgamerInsertResponse> roster = submitTeam.getRoster();
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
                submitTeam.getImageId(),
                0,
                0
        );
    }*/

}
