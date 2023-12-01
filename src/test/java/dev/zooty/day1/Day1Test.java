package dev.zooty.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    void getSolution1() {
        Day1 testDay1 = new Day1() {
            @Override
            public String getInputString() {
                return """
                        1abc2
                        pqr3stu8vwx
                        a1b2c3d4e5f
                        treb7uchet
                        """;
            }
        };
        assertEquals("142", testDay1.getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("281", new Day1().getSolution2());
    }
}