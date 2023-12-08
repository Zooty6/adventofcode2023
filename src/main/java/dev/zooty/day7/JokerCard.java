package dev.zooty.day7;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum JokerCard {
    cJ('J'),
    c2('2'),
    c3('3'),
    c4('4'),
    c5('5'),
    c6('6'),
    c7('7'),
    c8('8'),
    c9('9'),
    cT('T'),
    cQ('Q'),
    cK('K'),
    cA('A');
    private final char value;

    public static JokerCard of(char character) {
        return Arrays.stream(JokerCard.values())
                .filter(card -> card.value == character)
                .findAny()
                .orElseThrow();
    }
}
