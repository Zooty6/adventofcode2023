package dev.zooty.day7;

import lombok.Getter;

import java.util.List;

@Getter
public class CamelHand extends Hand {
    private final List<CamelCard> cards;

    public CamelHand(String deal) {
        cards = deal.chars()
                .asLongStream()
                .mapToObj(character -> CamelCard.of((char) character))
                .toList();
    }

    public Type getType() {
        if (type == null) {
            type = calculateType();
        }
        return type;
    }

    @Override
    Type calculateType() {
        return calculateTypeIgnoringJoker(cards
                .stream()
                .map(card -> JokerCard.of(card.getValue()))
                .toList());
    }

    @Override
    public int compareTo(Hand otherHand) {
        int firstRank = getType().compareTo(otherHand.getType());
        if (firstRank == 0) {
            int secondRank = 0;
            for (int i = 0; i < cards.size(); i++) {
                secondRank = cards.get(i).compareTo(((CamelHand) otherHand).cards.get(i));
                if (secondRank != 0) break;
            }
            return secondRank;
        }
        return firstRank;
    }
}
