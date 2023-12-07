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
        if (grouped.values().stream().anyMatch(aLong -> aLong == 5)) return Type.fiveOfAKind;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 4)) return Type.fourOfAKind;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 3) &&
                grouped.values().stream().anyMatch(aLong -> aLong == 2)) return Type.fullHouse;
        if (grouped.values().stream().anyMatch(aLong -> aLong == 3)) return Type.threeOfAKind;
        if (grouped.values().stream().filter(aLong -> aLong == 2).count() == 2) return Type.twoPair;
        if (grouped.values().stream().filter(aLong -> aLong == 2).count() == 1) return Type.onePair;
        return Type.highCard;
    }
}
