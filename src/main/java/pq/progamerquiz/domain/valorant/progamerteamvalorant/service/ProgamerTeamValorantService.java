package pq.progamerquiz.domain.valorant.progamerteamvalorant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.repository.ProgamerTeamValorantRepository;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProgamerTeamValorantService {

    private final ProgamerTeamValorantRepository progamerTeamValorantRepository;


    public TeamValorant findRecentTeamByProgamer(Long progamerId) {

        return null;
    }

    public List<TeamValorantSimpleInfoResponse> findTeamsByProgamerId(Long progamerId) {
        List<TeamValorantSimpleInfoResponse> teams = progamerTeamValorantRepository.findTeamsByProgamerId(progamerId);

        //시즌 연도 오름차순, 대회 순서 오름차순으로 정렬
        teams.sort(Comparator
                .comparing(TeamValorantSimpleInfoResponse::getSeasonYear)
                .thenComparing(response -> SEASON_ORDER.getOrDefault(response.getSeasonName(), Integer.MAX_VALUE)));

        return teams;
    }

    public List<ProgamerValorantSimpleInfoResponse> findProgamersByTeamId(Long teamId) {
        return progamerTeamValorantRepository.findProgamersByTeamId(teamId);
    }

    public List<Long> findTeamIdsWithFiveOrMoreProgamers() {
        return progamerTeamValorantRepository.findTeamIdsWithFiveOrMoreProgamers();
    }

    private static final Map<String, Integer> SEASON_ORDER = new LinkedHashMap<>() {{
        put("Kickoff", 1);
        put("Masters1", 2);
        put("Stage1", 3);
        put("Masters2", 4);
        put("Stage2", 5);
        put("Champions", 6);
    }};
}
