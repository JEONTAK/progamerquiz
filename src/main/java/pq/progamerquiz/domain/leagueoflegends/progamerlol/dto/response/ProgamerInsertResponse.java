package pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Position;

@Getter
@NoArgsConstructor
public class ProgamerInsertResponse {

    private Long id;
    private String progamerTag;
    private String name;
    private String birth;
    private Position position;
    private Long leagueWin;
    private Long intlWin;
    private String nationality;
    private String teamIds;

    private ProgamerInsertResponse(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality, String teamIds) {
        this.id = id;
        this.progamerTag = progamerTag;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.leagueWin = leagueWin;
        this.intlWin = intlWin;
        this.nationality = nationality;
        this.teamIds = teamIds;
    }

    public static ProgamerInsertResponse of(Long id, String progamerTag, String name, String birth, Position position, Long leagueWin, Long intlWin, String nationality, String teamIds) {
        return new ProgamerInsertResponse(id, progamerTag, name, birth, position, leagueWin, intlWin, nationality, teamIds);
    }
}