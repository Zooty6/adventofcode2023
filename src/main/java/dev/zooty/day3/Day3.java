package dev.zooty.day3;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day3 implements Day {
    private final int day = 3;
    private final EngineSchematic engineSchematic = new EngineSchematic(getInputString());

    @Override
    public String getSolution1() {
        return String.valueOf(engineSchematic
                .getPartNumbers()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(engineSchematic
                .getGearRations()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());
    }
}