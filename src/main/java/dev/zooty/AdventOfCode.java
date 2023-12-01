package dev.zooty;

import dev.zooty.day1.Day1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AdventOfCode {
    // TODO: find all days automatically
    private final static List<Day> days = List.of(
            new Day1()
    );

    /**
     * Runs the program, calculates every day's both solutions and prints it to the standard output.
     * If arguments are present, only those days are calculated
     *
     * @param args A list of numbers. Only those days will be processed.
     */
    public static void main(String[] args) {
        Predicate<Day> filter = day -> true;
        if(args.length != 0) {
            filter = day -> Arrays.stream(args).map(Integer::parseInt).toList().contains(day.getDay());
        }
        days.stream().filter(filter).forEach(day -> System.out.printf("Day %d solutions:%n    1: %s, 2: %s%n", day.getDay(), day.getSolution1(), day.getSolution2()));
    }
}

