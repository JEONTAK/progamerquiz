package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.response;

import lombok.Getter;

@Getter
public class LOLHintResult {

    private final String hintName;
    private final String inputValue;
    private final String answerValue;
    private final boolean isMatch;

    private LOLHintResult(String hintName, String inputValue, String answerValue, boolean isMatch) {
        this.hintName = hintName;
        this.inputValue = inputValue;
        this.answerValue = answerValue;
        this.isMatch = isMatch;
    }

    public static LOLHintResult of(String hintName, String inputValue, String answerValue, boolean isMatch) {
        return new LOLHintResult(hintName, inputValue, answerValue, isMatch);
    }
}
