package dev.zooty.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {

    @Test
    void getSolution1() {
        assertEquals("136", new Day14().getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("64", new Day14().getSolution2());
    }

    @Test
    void balanceOnce() {
        ReflectorDish reflectorDish = new ReflectorDish(new Day14().getInputReader());
        assertEquals("87", String.valueOf(reflectorDish.balance(1)));
    }

    @Test
    void balanceTwice() {
        ReflectorDish reflectorDish = new ReflectorDish(new Day14().getInputReader());
        assertEquals("69", String.valueOf(reflectorDish.balance(2)));
    }
}