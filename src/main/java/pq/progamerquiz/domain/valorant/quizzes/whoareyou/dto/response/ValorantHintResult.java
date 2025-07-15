package pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.response;

import lombok.Getter;

@Getter
public class ValorantHintResult {

    private final String hintName;
    private final String inputValue;
    private final String answerValue;
    private final boolean isMatch;

    private ValorantHintResult(String hintName, String inputValue, String answerValue, boolean isMatch) {
        this.hintName = hintName;
        this.inputValue = inputValue;
        this.answerValue = answerValue;
        this.isMatch = isMatch;
    }

    public static ValorantHintResult of(String hintName, String inputValue, String answerValue, boolean isMatch) {
        return new ValorantHintResult(hintName, inputValue, answerValue, isMatch);
    }
}
