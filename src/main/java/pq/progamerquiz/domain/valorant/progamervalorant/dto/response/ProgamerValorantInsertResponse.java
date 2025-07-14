package pq.progamerquiz.domain.valorant.progamervalorant.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgamerValorantInsertResponse {

    private Long id;
    private String progamerTag;
    private String name;
    private String birth;
    private Long leagueWin;
    private Long mastersWin;
    private Long championsWin;
    private String nationality;
    private String teams;

    private ProgamerValorantInsertResponse(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin, String nationality, String teams) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.leagueWin = leagueWin;
        this.mastersWin = mastersWin;
        this.championsWin = championsWin;
        this.nationality = nationality;
        this.teams = teams;
    }

    public static ProgamerValorantInsertResponse of(Long id, String progamerTag, String name, String birth, Long leagueWin, Long mastersWin, Long championsWin, String nationality, String teams) {
        return new ProgamerValorantInsertResponse(id, progamerTag, name, birth, leagueWin, mastersWin, championsWin, nationality, teams);
    }
}