package dev.zooty.day7;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Hand implements Comparable<Hand> {

    Type type;
    public abstract Type getType();
    abstract Type calculateType();

    Type calculateTypeIgnoringJoker(List<JokerCard> cards) {
        var grouped = cards.parallelStream()
                .collect(Collectors.groupingBy(card -> card, Collectors.counting()));
        if (grouped.values().stream().anyMatch(aLong -> aLong == 5)) return Type.FIVE_OF_A_KIND;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 4)) return Type.FOUR_OF_A_KIND;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 3) &&
                grouped.values().stream().anyMatch(aLong -> aLong == 2)) return Type.FULL_HOUSE;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 3)) return Type.THREE_OF_A_KIND;
        if (grouped.values().stream().filter(aLong -> aLong == 2).count() == 2) return Type.TWO_PAIR;
        if (grouped.values().stream().filter(aLong -> aLong == 2).count() == 1) return Type.ONE_PAIR;
        return Type.HIGH_CARD;
    }
}
