package pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response;

import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamLOLInfoResponse;

@Getter
public class ProgamerLOLWithRecentTeamResponse {

    private final Long id;
    private final String progamerTag;
    private final String name;
    private final String birth;
    private final Position position;
    private final Long leagueWin;
    private final Long intlWin;
    private final String nationality;
    private final TeamLOLInfoResponse recentTeam;

    private ProgamerLOLWithRecentTeamResponse(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality, TeamLOLInfoResponse recentTeam) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.leagueWin = leagueWin;
        this.intlWin = intlWin;
        this.nationality = nationality;
        this.recentTeam = recentTeam;
    }

    public static ProgamerLOLWithRecentTeamResponse of(Long id, String progamerTag, String name, String birth, Position position, Long league_win, Long intl_win, String nationality, TeamLOLInfoResponse recentTeam) {
        return new ProgamerLOLWithRecentTeamResponse(id, progamerTag, name, birth, position, league_win, intl_win, nationality, recentTeam);
    }
}