package dev.zooty.day10;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day10 implements Day {
    private final int day = 10;
    @Override
    public String getSolution1() {
        return String.valueOf(new PipeMap(getInputString()).getMaxDistance());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(new PipeMap(getInputString()).getEnclosedTiles());
    }
}
