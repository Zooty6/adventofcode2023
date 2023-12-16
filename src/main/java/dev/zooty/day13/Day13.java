package dev.zooty.day13;

import dev.zooty.Day;
import dev.zooty.Ignored;
import lombok.Getter;

@Getter
public class Day13 implements Day {
    private int day = 13;

    @Override
    @Ignored(reason = "Not implemented yet")
    public String getSolution1() {
        return String.valueOf(new LavaLand(getInputString()).summarize());
    }

    @Override
    @Ignored(reason = "Not implemented yet")
    public String getSolution2() {
        return null;
    }
}
