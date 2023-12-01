package dev.zooty.day1;

import dev.zooty.Day;
import lombok.Getter;


@Getter
public class Day1 implements Day {
    final int day = 1;
    @Override
    public String getSolution1() {
        return String.valueOf(getInputString().lines()
                .map(CalibrationValue::new)
                .map(CalibrationValue::getCalibrationValue)
                .mapToInt(Integer::intValue)
                .sum());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(getInputString().lines()
                .map(CalibrationValueWithLetters::new)
                .map(CalibrationValueWithLetters::getCalibrationValue)
                .mapToInt(Integer::intValue)
                .sum());
    }
}
