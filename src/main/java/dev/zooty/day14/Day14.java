package dev.zooty.day14;

import dev.zooty.Day;
import lombok.Getter;

@Getter
public class Day14 implements Day {
    private final int day = 14;

    @Override
    public String getSolution1() {
        ReflectorDish reflectorDish = new ReflectorDish(getInputReader());
        reflectorDish.tilt();
        return String.valueOf(reflectorDish.getTotalLoad());
    }

    @Override
    public String getSolution2() {
        ReflectorDish reflectorDish = new ReflectorDish(getInputReader());
        return String.valueOf(reflectorDish.balance(1000)); // for some reason works with 100 cycles ¯\_(ツ)_/¯
    }
}
