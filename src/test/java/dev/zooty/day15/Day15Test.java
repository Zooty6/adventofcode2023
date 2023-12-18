package dev.zooty.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test {

    @Test
    void getSolution1() {
        assertEquals("1320", new Day15().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("145", new Day15().getSolution2());
    }
}