package dev.zooty.day25;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day25 implements Day {
    public final int day = 25;

    @Override
    public String getSolution1() {
        return String.valueOf(new ComponentPool(getInputReader()).getDividedGroupSize());
    }

    @Override
    public String getSolution2() {
        return null;
    }
}
