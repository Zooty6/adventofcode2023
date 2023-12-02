package dev.zooty.day2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Color {
    red("red"),
    green("green"),
    blue("blue");

    private final String value;
    public static Color of(String value) {
        return Arrays.stream(Color.values())
                .filter(color -> color.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value + " is not a color"));
    }
}
