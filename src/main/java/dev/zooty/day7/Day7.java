package dev.zooty.day7;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day7 implements Day {
    private final int day = 7;

    @Override
    public String getSolution1() {
        return String.valueOf(new PokerParty(getInputReader(), Game.GameType.camel).getTotalWinning());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(new PokerParty(getInputReader(), Game.GameType.joker).getTotalWinning());
    }
}
