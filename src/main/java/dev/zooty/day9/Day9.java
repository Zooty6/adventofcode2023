package dev.zooty.day9;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day9 implements Day {
    private final int day = 9;

    @Override
    public String getSolution1() {
        return String.valueOf(new OASIS(getInputReader())
                .getHistories()
                .parallelStream()
                .mapToInt(OasisHistory::getExtrapolate)
                .sum());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(new OASIS(getInputReader())
                .getHistories()
                .parallelStream()
                .mapToInt(OasisHistory::getExtrapolateBack)
                .sum());
    }
}
