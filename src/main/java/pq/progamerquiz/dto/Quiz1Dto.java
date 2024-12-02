package pq.progamerquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Quiz : Who are you?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Quiz1Dto {

    private Long id;
    private String pid;
    private String name;
    private Long birth;
    private String position;
    private Long league_win;
    private Long intl_win;
    private String nationality;
    private String recentTeam;
    private Long teamId;
    private String recentLeague;

    public static Quiz1Dto convert(ProgamerDto submitProgamer) {
        return new Quiz1Dto(
                submitProgamer.getId(),
                submitProgamer.getPid(),
                submitProgamer.getName(),
                submitProgamer.getBirth(),
                submitProgamer.getPosition(),
                submitProgamer.getLeague_win(),
                submitProgamer.getIntl_win(),
                submitProgamer.getNationality(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getName(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getImage_path(),
                submitProgamer.getTeams().get(submitProgamer.getTeams().size() - 1).getLeague().toString()
        );
    }
}
