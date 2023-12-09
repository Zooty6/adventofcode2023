package dev.zooty.day7;

import lombok.Getter;

@Getter
public class Game implements Comparable<Game> {
    private final int bid;
    private final Hand hand;

    public Game(String line, GameType gameType) {
        String[] split = line.split(" ");
        hand = gameType == GameType.CAMEL ? new CamelHand(split[0]) : new JokerHand(split[0]);
        bid = Integer.parseInt(split[1]);
    }

    @Override
    public int compareTo(Game other) {
        return this.getHand().compareTo(other.getHand());
    }

    public enum GameType {
        CAMEL,
        JOKER
    }
}
