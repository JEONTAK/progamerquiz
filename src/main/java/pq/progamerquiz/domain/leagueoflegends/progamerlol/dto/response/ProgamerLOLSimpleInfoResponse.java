package pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgamerLOLSimpleInfoResponse {

    private Long id;
    private String progamerTag;

    private ProgamerLOLSimpleInfoResponse(Long id, String progamerTag) {
        this.id = id;
        this.progamerTag = progamerTag;
    }

    public static ProgamerLOLSimpleInfoResponse of(Long id, String progamerTag) {
        return new ProgamerLOLSimpleInfoResponse(id, progamerTag);
    }
}