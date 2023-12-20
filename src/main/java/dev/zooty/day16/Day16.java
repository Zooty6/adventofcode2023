package dev.zooty.day16;

import dev.zooty.Day;
import dev.zooty.Ignored;
import lombok.Getter;

@Getter
public class Day16 implements Day {
    private final int day = 16;

    @Override
    public String getSolution1() {
        return String.valueOf(new Cave(getInputReader()).countEnergizedTiles());
    }

    @Override
    @Ignored(reason = "Takes about 15 minutes to run")
    public String getSolution2() {
        return String.valueOf(new Cave(getInputReader()).countEnergizedTilesFromAllDirection());
    }
}
