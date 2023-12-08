package dev.zooty.day8;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day8 implements Day {
    private final int day = 8;

    @Override
    public String getSolution1() {
        return String.valueOf(new Map(getInputString()).getMinStepsToZ());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(new Map(getInputString()).getMinStepsToZForAllA());
    }
}
