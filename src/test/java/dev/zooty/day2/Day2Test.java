package dev.zooty.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    @Test
    void getSolution1() {
        assertEquals("8", new Day2().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("2286", new Day2().getSolution2());
    }
}