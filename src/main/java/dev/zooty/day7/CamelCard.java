package dev.zooty.day7;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CamelCard {
    CARD_2('2'),
    CARD_3('3'),
    CARD_4('4'),
    CARD_5('5'),
    CARD_6('6'),
    CARD_7('7'),
    CARD_8('8'),
    CARD_9('9'),
    CARD_T('T'),
    CARD_J('J'),
    CARD_Q('Q'),
    CARD_K('K'),
    CARD_A('A');
    private final char value;

    public static CamelCard of(char character) {
        return Arrays.stream(CamelCard.values())
                .filter(card -> card.value == character)
                .findAny()
                .orElseThrow();
    }
}
