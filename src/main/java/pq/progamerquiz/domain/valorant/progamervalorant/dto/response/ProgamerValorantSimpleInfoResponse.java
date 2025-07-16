package pq.progamerquiz.domain.valorant.progamervalorant.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgamerValorantSimpleInfoResponse {

    private Long id;
    private String progamerTag;

    private ProgamerValorantSimpleInfoResponse(Long id, String progamerTag) {
        this.id = id;
        this.progamerTag = progamerTag;
    }

    public static ProgamerValorantSimpleInfoResponse of(Long id, String progamerTag) {
        return new ProgamerValorantSimpleInfoResponse(id, progamerTag);
    }
}