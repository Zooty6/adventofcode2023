package dev.zooty.day15;

import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Step {
    private static final String STEP_REGEX = "(?<label>.*)(?<operation>[=|-])(?<focalLength>\\d?)";
    private static final Pattern stepPattern = Pattern.compile(STEP_REGEX);
    private final String chars;
    private int currentHashValue = 0;
    private final String label;
    private final Operation operation;
    private final Integer focalLength;

    public Step(String input) {
        chars = input;
        Matcher matcher = stepPattern.matcher(input);
        if (matcher.matches()) {
            label = matcher.group("label");
            operation = Operation.of(matcher.group("operation"));
            focalLength = matcher.group("focalLength").isEmpty() ?
                    null :
                    Integer.valueOf(matcher.group("focalLength"));
        } else {
            throw new IllegalArgumentException("Invalid input: %s".formatted(input));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Step step && step.label.equals(this.label);
    }

    public int getLabelHash() {
        this.currentHashValue = 0;
        label.chars().forEach(this::hashCharacter);
        return currentHashValue;
    }

    @Override
    public int hashCode() {
        this.currentHashValue = 0;
        chars.chars().forEach(this::hashCharacter);
        return currentHashValue;
    }

    private void hashCharacter(int character) {
        currentHashValue += character;
        currentHashValue *= 17;
        currentHashValue %= 256;
    }

     enum Operation {
        EQUALS("="),
        DASH("-");

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public static Operation of(String symbol) {
            return Arrays.stream(Operation.values())
                    .filter(operation -> operation.symbol.equals(symbol))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
