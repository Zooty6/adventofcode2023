package dev.zooty.day2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class HomogeneousCubeSet {
    private final static String regex = "(?<amount>\\d+)\\s(?<color>.+)";
    private final static Pattern pattern = Pattern.compile(regex);
    private final Color color;
    private final int amount;

    public HomogeneousCubeSet(String line) {
        MatchResult matcher = pattern.matcher(line)
                .results()
                .findAny()
                .orElseThrow();
        amount = Integer.parseInt(matcher.group("amount"));
        color = Color.of(matcher.group("color"));
    }
}
