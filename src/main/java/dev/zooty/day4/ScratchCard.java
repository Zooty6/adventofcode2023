package dev.zooty.day4;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Getter
public class ScratchCard {
    private final static String cardRegex =
            "(Card\\s+)(?<id>\\d+)(:\\s+)(?<winningNumbers>(\\d+\\s+)+)(\\|\\s+)(?<numbers>(\\d+\\s*)+)";
    private final static Pattern cardPattern = Pattern.compile(cardRegex);
    private final int id;
    private final List<Integer> winningNumbers;
    private final List<Integer> numbers;
    @Setter
    private int copyAmount = 1;

    public ScratchCard(int id, List<Integer> winningNumbers, List<Integer> numbers) {
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.numbers = numbers;
    }

    public ScratchCard(String input) {
        MatchResult matchResult = cardPattern.matcher(input)
                .results()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can't parse " + input));
        id = Integer.parseInt(matchResult.group("id"));
        winningNumbers = Arrays.stream(matchResult.group("winningNumbers").split(" "))
                .filter(number -> !number.isEmpty())
                .map(Integer::parseInt)
                .toList();
        numbers = Arrays.stream(matchResult.group("numbers").split(" "))
                .filter(number -> !number.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }

    public long getWinningCount() {
        return numbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    public int getPoints() {
        return (int) Math.pow(2, getWinningCount() - 1);
    }

    public ScratchCard copy() {
        return new ScratchCard(this.id, List.copyOf(this.winningNumbers), List.copyOf(this.numbers));
    }

}
