package pq.progamerquiz.domain.whoareyou.dto;

import lombok.*;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;

import java.util.Optional;

//Quiz : Who are you?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WheAreYouDto {

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

    public static WheAreYouDto convert(Optional<ProgamerDto> submitProgamer) {
        return submitProgamer.map(progamer ->
                new WheAreYouDto(
                        progamer.getId(),
                        progamer.getPid(),
                        progamer.getName(),
                        progamer.getBirth(),
                        progamer.getPosition(),
                        progamer.getLeague_win(),
                        progamer.getIntl_win(),
                        progamer.getNationality(),
                        progamer.getTeams().isEmpty() ? null : progamer.getTeams().get(progamer.getTeams().size() - 1).getName(),
                        progamer.getTeams().isEmpty() ? null : progamer.getTeams().get(progamer.getTeams().size() - 1).getImage_path(),
                        progamer.getTeams().isEmpty() ? null : progamer.getTeams().get(progamer.getTeams().size() - 1).getLeague().toString()
                )
        ).orElseThrow(() -> new IllegalArgumentException("ProgamerDto is required"));
    }
}
