package dev.zooty.day7;

import lombok.Getter;

import java.io.BufferedReader;
import java.util.List;

@Getter
public class PokerParty {
    private final List<Game> games;

    public PokerParty(BufferedReader input, Game.GameType gameType) {
        games = input
                .lines()
                .map((String line) -> new Game(line, gameType))
                .sorted()
                .toList();
    }

    public int getTotalWinning() {
        int sum = 0;
        for (int i = 0; i < games.size(); i++) {
            sum = sum + (i + 1) * games.get(i).getBid();
        }
        return sum;
    }
}
