package dev.zooty.day7;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static dev.zooty.day7.JokerCard.cJ;

@Getter
public class JokerHand extends Hand {
    private final List<JokerCard> cards;

    public JokerHand(String deal) {
        cards = deal.chars()
                .asLongStream()
                .mapToObj(character -> JokerCard.of((char) character))
                .toList();
    }

    @Override
    public Type getType() {
        if (type == null) {
            type = calculateType();
        }
        return type;
    }

    @Override
    Type calculateType() {
        if (cards.contains(cJ)) {
            return Arrays.stream(JokerCard.values())
                    .parallel()
                    .map(newJokerCard -> {
                        var replacingList = new ArrayList<>(cards);
                        replacingList.replaceAll(replacing -> replacing == cJ ? newJokerCard : replacing);
                        return replacingList;
                    })
                    .map(this::calculateTypeIgnoringJoker)
                    .max(Comparator.naturalOrder())
                    .orElseThrow();
        }

        return calculateTypeIgnoringJoker(cards);
    }

    @Override
    public int compareTo(Hand otherHand) {
        int firstRank = getType().compareTo(otherHand.getType());
        if (firstRank == 0) {
            int secondRank = 0;
            for (int i = 0; i < cards.size(); i++) {
                secondRank = cards.get(i).compareTo(((JokerHand) otherHand).cards.get(i));
                if (secondRank != 0) break;
            }
            return secondRank;
        }
        return firstRank;
    }
}
