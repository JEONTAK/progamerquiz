package pq.progamerquiz.domain.progamer.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProgamerSimpleInfoResponse {

    private Long id;
    private String progamerTag;

    private ProgamerSimpleInfoResponse(Long id, String progamerTag) {
        this.id = id;
        this.progamerTag = progamerTag;
    }

    public static ProgamerSimpleInfoResponse of(Long id, String progamerTag) {
        return new ProgamerSimpleInfoResponse(id, progamerTag);
    }
}