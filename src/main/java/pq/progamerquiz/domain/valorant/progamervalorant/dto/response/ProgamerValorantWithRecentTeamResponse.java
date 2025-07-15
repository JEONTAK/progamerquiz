package pq.progamerquiz.domain.valorant.progamervalorant.dto.response;

import lombok.Getter;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantInfoResponse;

@Getter
public class ProgamerValorantWithRecentTeamResponse {

    private final Long id;
    private final String progamerTag;
    private final String name;
    private final String birth;
    private final Long leagueWin;
    private final Long mastersWin;
    private final Long championsWin;
    private final String nationality;
    private final TeamValorantInfoResponse recentTeam;

    private ProgamerValorantWithRecentTeamResponse(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin, String nationality, TeamValorantInfoResponse recentTeam) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.leagueWin = leagueWin;
        this.mastersWin = mastersWin;
        this.championsWin = championsWin;
        this.nationality = nationality;
        this.recentTeam = recentTeam;
    }

    public static ProgamerValorantWithRecentTeamResponse of(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin, String nationality, TeamValorantInfoResponse recentTeam) {
        return new ProgamerValorantWithRecentTeamResponse(id, progamerTag, name, birth, leagueWin, mastersWin, championsWin, nationality, recentTeam);
    }
}