package dev.zooty.day7;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CamelCard {
    c2('2'),
    c3('3'),
    c4('4'),
    c5('5'),
    c6('6'),
    c7('7'),
    c8('8'),
    c9('9'),
    cT('T'),
    cJ('J'),
    cQ('Q'),
    cK('K'),
    cA('A');
    private final char value;

    CamelCard(char value) {
        this.value = value;
    }

    public static CamelCard of(char character) {
        return Arrays.stream(CamelCard.values())
                .filter(card -> card.value == character)
                .findAny()
                .orElseThrow();
    }
}
