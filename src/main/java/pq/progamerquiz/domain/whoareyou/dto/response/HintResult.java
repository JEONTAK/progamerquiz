package pq.progamerquiz.domain.whoareyou.dto.response;

import lombok.Getter;

@Getter
public class HintResult {

    private final String hintName;
    private final String inputValue;
    private final String answerValue;
    private final boolean isMatch;

    private HintResult(String hintName, String inputValue, String answerValue, boolean isMatch) {
        this.hintName = hintName;
        this.inputValue = inputValue;
        this.answerValue = answerValue;
        this.isMatch = isMatch;
    }

    public static HintResult of(String hintName, String inputValue, String answerValue, boolean isMatch) {
        return new HintResult(hintName, inputValue, answerValue, isMatch);
    }
}
