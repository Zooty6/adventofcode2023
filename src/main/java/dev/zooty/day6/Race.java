package dev.zooty.day6;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Race {
    private static final String TIME_REGEX = "Time:\\s+(?<data>(\\d+\\s*)+)";
    private static final String DISTANCE_REGEX = "Distance:\\s+(?<data>(\\d+\\s*)+)";
    private static final Pattern timePattern = Pattern.compile(TIME_REGEX);
    private static final Pattern distancePattern = Pattern.compile(DISTANCE_REGEX);
    private final List<Integer> times = new ArrayList<>();
    private final List<Integer> distances = new ArrayList<>();
    private final List<Game> games = new ArrayList<>();
    public Race(BufferedReader inputReader) {
        Iterator<String> lineIterator = inputReader.lines().iterator();
        parseLine(lineIterator.next(), timePattern, times);
        parseLine(lineIterator.next(), distancePattern, distances);
        for (int i = 0; i < times.size(); i++) {
            games.add(new Game(times.get(i), distances.get(i)));
        }
    }

    public Long getWaysToWinForMultiple() {
        return games.parallelStream()
                .mapToLong(this::numberOfWaysToWin)
                .reduce(1, Math::multiplyExact);
    }

    public long getWaysToWinSingle() {
        long time = Long.parseLong(times
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));
        long distance = Long.parseLong(distances
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));
        return numberOfWaysToWin(new Game(time, distance));
    }

    private Long numberOfWaysToWin(Game game) {
        return LongStream.range(1, game.getTime())
                .filter(timeSpent -> (game.getTime() - timeSpent) * timeSpent > game.getDistance())
                .count();
    }

    private void parseLine(String line, Pattern pattern, List<Integer> destinationList) {
        Arrays.stream(pattern
                        .matcher(line)
                        .results()
                        .findAny()
                        .orElseThrow()
                        .group("data")
                        .split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(() -> destinationList));
    }
}
