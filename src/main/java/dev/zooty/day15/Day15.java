package dev.zooty.day15;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day15 implements Day {
    private final int day = 15;

    @Override
    public String getSolution1() {
        return String.valueOf(new HASHMAP(getInputString()).getSumOfResults());
    }

    @Override
    public String getSolution2() {
        HASHMAP hashmap = new HASHMAP(getInputString());
        hashmap.configure();
        return String.valueOf(hashmap.calculatePower());
    }
}
