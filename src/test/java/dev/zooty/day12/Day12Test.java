package dev.zooty.day12;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    @Test
    void testSolution1Partial() {
        assertEquals("1", new Day12() {
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader("???.### 1,1,3"));
            }
        }.getSolution1());

        assertEquals("4", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader(".??..??...?##. 1,1,3"));
            }
        }.getSolution1());

        assertEquals("1", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader( "?#?#?#?#?#?#?#? 1,3,1,6"));
            }
        }.getSolution1());


        assertEquals("1", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader( "????.#...#... 4,1,1"));
            }
        }.getSolution1());

        assertEquals("4", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader("????.######..#####. 1,6,5"));
            }
        }.getSolution1());

        assertEquals("10", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader("?###???????? 3,2,1"));
            }
        }.getSolution1());
    }
    @Test
    void getSolution1() {
        assertEquals("10", new Day12().getSolution1());
    }

    @Test
    @Disabled("not working yet")
    void getSolution2() {
        assertEquals("1", new Day12(){
            @Override
            public BufferedReader getInputReader() {
                return new BufferedReader(new StringReader("???.### 1,1,3"));
            }
        }.getSolution2());
    }
}