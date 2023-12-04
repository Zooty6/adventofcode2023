package dev.zooty.day4;

import lombok.Getter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TableOfScratchCards {
    private final List<ScratchCard> cards = new ArrayList<>();

    public TableOfScratchCards(BufferedReader inputReader) {
        inputReader.lines().forEach(line -> cards.add(new ScratchCard(line)));
    }

    public int getTotalScore() {
        return cards.stream()
                .mapToInt(ScratchCard::getPoints)
                .sum();
    }

    /**
     * @deprecated as it runs with a too high complexity, resulting it not finishing in a desired time frame.
     */
    @Deprecated
    public void processByModelling() {
        for (int cardIterator = 0; cardIterator < cards.size(); cardIterator++) {
            var scratchCard = cards.get(cardIterator);
            for (int i = 1; i <= scratchCard.getWinningCount(); i++) {
                int comparingId = i + scratchCard.getId();
                cards.add(cardIterator + i, cards.stream()
                        .filter(filteringCard -> filteringCard.getId() == comparingId)
                        .findAny()
                        .orElseThrow(() -> new IllegalStateException("Scratchcard %d wants to copy %d but can't find any".formatted(scratchCard.getId(), comparingId)))
                        .copy());
            }
        }
    }

    public void processByCopyAmounts() {
        for (ScratchCard scratchCard : cards) {
            for (int i = 1; i <= scratchCard.getWinningCount(); i++) {
                int comparingId = i + scratchCard.getId();
                ScratchCard copyingCard = cards.stream()
                        .filter(filteringCard -> filteringCard.getId() == comparingId)
                        .findAny()
                        .orElseThrow(() -> new IllegalStateException("Scratchcard %d wants to copy %d but can't find any".formatted(scratchCard.getId(), comparingId)));
                copyingCard.setCopyAmount(copyingCard.getCopyAmount() + scratchCard.getCopyAmount());
            }
        }
    }

    public int getCardsOnTheTable() {
        return Math.max(cards.size(), cards.stream().mapToInt(ScratchCard::getCopyAmount).sum());
    }
}
