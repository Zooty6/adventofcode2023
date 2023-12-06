package dev.zooty.day6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

    @Test
    void getSolution1() {
        assertEquals("288", new Day6().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("71503", new Day6().getSolution2());
    }
}