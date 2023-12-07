package dev.zooty.day7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    @Test
    void getSolution1() {
        assertEquals("6440", new Day7().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("5905", new Day7().getSolution2());
    }
}