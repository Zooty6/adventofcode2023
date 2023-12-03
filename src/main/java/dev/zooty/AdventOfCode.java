package dev.zooty;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.reflections.scanners.Scanners.SubTypes;

public class AdventOfCode {

    /**
     * Runs the program, calculates every day's both solutions and prints it to the standard output.
     * If arguments are present, only those days are calculated
     *
     * @param args A list of numbers. Only those days will be processed.
     */
    public static void main(String[] args) {
        getDaySolutions()
                .filter(filterByAppArguments(args))
                .forEach(day -> System.out.printf("Day %d solutions:%n    1: %s, 2: %s%n", day.getDay(), day.getSolution1(), day.getSolution2()));
    }

    private static Predicate<Day> filterByAppArguments(String[] args) {
        return args.length > 0 ?
                day -> Arrays.stream(args).map(Integer::parseInt).toList().contains(day.getDay()) :
                day -> true;
    }

    @SuppressWarnings("unchecked")
    private static Stream<Day> getDaySolutions() {
        return new Reflections("dev.zooty")
                .get(SubTypes.of(Day.class).asClass())
                .stream()
                .filter(aClass -> Arrays.asList(aClass.getInterfaces()).contains(Day.class))
                .map(aClass -> construct((Class<Day>) aClass))
                .sorted(Comparator.comparingInt(Day::getDay));
    }

    @SneakyThrows
    private static Day construct(Class<Day> aClass) {
        return aClass.getDeclaredConstructor().newInstance();
    }
}

