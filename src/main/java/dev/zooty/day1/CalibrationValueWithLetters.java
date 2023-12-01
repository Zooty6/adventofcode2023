package dev.zooty.day1;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public class CalibrationValueWithLetters {
    private final int firstValue;
    private final int secondValue;
    private static final Map<String, Integer> digits = new HashMap<>();
    private static final Map<String, Integer> reversedDigits = new HashMap<>();
    private static final String digitRegex;
    private static final Pattern digitPattern;
    private static final String reversedRegex;
    private static final Pattern reversedPattern;

    public CalibrationValueWithLetters(String line) {
        firstValue = calculateFirstValue(line);
        secondValue = calculateSecondValue(line);
    }

    public int getCalibrationValue() {
        return firstValue * 10 + secondValue;
    }

    private int calculateFirstValue(String line) {
        return getDigitsInLine(line).getFirst();
    }

    private int calculateSecondValue(String line) {
        List<Integer> digitsInLineReverse = getDigitsInLineReverse(line);
        System.out.println(digitsInLineReverse.stream().map(String::valueOf).collect(Collectors.joining(",")));
        return getDigitsInLineReverse(line).getFirst();
    }

    private List<Integer> getDigitsInLine(String line) {
           return digitPattern
                .matcher(line)
                .results()
                .map(MatchResult::group)
                .map(digits::get)
                .collect(Collectors.toList());
    }

    private List<Integer> getDigitsInLineReverse(String line) {
        return reversedPattern
                .matcher(new StringBuilder(line).reverse())
                .results()
                .map(MatchResult::group)
                .map(reversedDigits::get)
                .collect(Collectors.toList());
    }


    static {
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);
        digits.put("1", 1);
        digits.put("2", 2);
        digits.put("3", 3);
        digits.put("4", 4);
        digits.put("5", 5);
        digits.put("6", 6);
        digits.put("7", 7);
        digits.put("8", 8);
        digits.put("9", 9);
        digitRegex = '(' + String.join("|", digits.keySet()) + ")";
        digitPattern = Pattern.compile(digitRegex);
        digits.keySet().forEach(key -> reversedDigits.put(new StringBuilder(key).reverse().toString(), digits.get(key)));
        reversedRegex = '(' + String.join("|", reversedDigits.keySet()) + ")";
        reversedPattern = Pattern.compile(reversedRegex);
    }
}
