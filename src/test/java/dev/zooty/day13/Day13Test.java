package dev.zooty.day13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    @Test
    void getSolution1() {
        assertEquals("405", new Day13().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("400", new Day13().getSolution2());
    }
}