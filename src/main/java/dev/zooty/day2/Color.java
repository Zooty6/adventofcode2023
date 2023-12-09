package dev.zooty.day2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Color {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private final String value;
    public static Color of(String value) {
        return Arrays.stream(Color.values())
                .filter(color -> color.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value + " is not a color"));
    }
}
