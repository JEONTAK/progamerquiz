package pq.progamerquiz.domain.quizzes.whichisteam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.team.dto.response.TeamInsertResponse;

//Quiz : What is Team?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WhichIsTeamResponse {
    private Long index;
    private Long teamId;
    private String teamName;
    private String callName;
    private String teamLeague;
    private Long teamYear;
    private Long spring_ranking;
    private Long summer_ranking;
    private Long winter_ranking;
    private Long worlds_ranking;
    private Long msi_ranking;
    private Long image_path;

    public static WhichIsTeamResponse of(int idx, TeamInsertResponse submitTeam) {
        return new WhichIsTeamResponse(
                (long) idx - 1,
                submitTeam.getId(),
                submitTeam.getName(),
                submitTeam.getCallName(),
                submitTeam.getLeague().toString(),
                submitTeam.getSeasonYear(),
                submitTeam.getSpringRank(),
                submitTeam.getSummerRank(),
                submitTeam.getWinterRank(),
                submitTeam.getWorldsRank(),
                submitTeam.getMsiRank(),
                submitTeam.getImageId()
        );
    }
}
