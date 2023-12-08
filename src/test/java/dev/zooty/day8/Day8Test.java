package dev.zooty.day8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {

    @Test
    void getSolution1() {
        assertEquals("6", new Day8(){
            @Override
            public String getInputString() {
                return """
                         LLR
                                                
                         AAA = (BBB, BBB)
                         BBB = (AAA, ZZZ)
                         ZZZ = (ZZZ, ZZZ)
                         """;
            }
        }.getSolution1());
    }

    @Test
    void getSolution2() {
        assertEquals("6", new Day8().getSolution2());
    }
}