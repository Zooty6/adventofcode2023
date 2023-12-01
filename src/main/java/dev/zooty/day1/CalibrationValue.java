package dev.zooty.day1;

import lombok.Getter;


@Getter
public class CalibrationValue {
    private final int firstValue;
    private final int secondValue;
    private static final int CHAR0 = 48;
    private static final int CHAR9 = 57;

    public CalibrationValue(String line) {
        firstValue = calculateFirstValue(line);
        secondValue = calculateSecondValue(line);
    }

    public int getCalibrationValue() {
        return firstValue * 10 + secondValue;
    }

    private static boolean isDigit(int value) {
        return value >= CHAR0 && value <= CHAR9;
    }

    private int calculateFirstValue(String line) {
        return line
                .chars()
                .filter(CalibrationValue::isDigit)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No digit found in " + line)) - CHAR0;
    }

    private int calculateSecondValue(String line) {
        return line
                .chars()
                .filter(CalibrationValue::isDigit)
                .reduce((first, last) -> last)
                .orElseThrow(() -> new IllegalArgumentException("No digit found in " + line)) - CHAR0;
    }

}
