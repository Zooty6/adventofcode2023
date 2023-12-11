package dev.zooty.day11;

import dev.zooty.day10.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Observatory {
    private static final char GALAXY = '#';
    private List<String> map;
    private List<Coordinates> galaxies;
    private final List<Integer> emptyColumns;
    private final List<Integer> emptyRows;

    public Observatory(String map) {
        this.map = List.of(map.split("\n"));
        findGalaxies();
        emptyRows = findEmptyRows();
        emptyColumns = findEmptyColumns();
    }

    public long getDistances(int expandRatio) {
        expandRatio = Math.max(1, expandRatio);
        long distance = 0;
        for (Coordinates galaxy1 : galaxies) {
            for (Coordinates galaxy2 : galaxies) {
                if (galaxy1 != galaxy2) {
                    distance += Math.abs(galaxy1.x() - galaxy2.x()) +
                            Math.abs(galaxy1.y() - galaxy2.y()) +
                            (expandRatio -1) * getEmptyRowsBetween(galaxy1, galaxy2) +
                            (expandRatio -1) * getEmptyColumnsBetween(galaxy1, galaxy2);
                }
            }
        }
        return distance / 2;
    }

    /**
     * @deprecated can be done much faster by math
     */
    @Deprecated(since = "1")
    public void expand() {
        expandHorizontal();
        expandVertical();
        findGalaxies();
    }

    private long getEmptyRowsBetween(Coordinates galaxy1, Coordinates galaxy2) {
        return emptyRows.stream()
                .filter(integer -> Math.min(galaxy1.x(), galaxy2.x()) < integer && integer < Math.max(galaxy1.x(), galaxy2.x()))
                .count();
    }

    private long getEmptyColumnsBetween(Coordinates galaxy1, Coordinates galaxy2) {
        return emptyColumns.stream()
                .filter(integer -> Math.min(galaxy1.y(), galaxy2.y()) < integer && integer < Math.max(galaxy1.y(), galaxy2.y()))
                .count();
    }

    private List<Integer> findEmptyColumns() {
        return IntStream.range(0, map.get(0).length())
                .filter(rowNumber -> map
                        .stream()
                        .allMatch(line -> line.charAt(rowNumber) == '.'))
                .boxed()
                .toList();
    }

    private List<Integer> findEmptyRows() {
        return IntStream.range(0, map.size())
                .filter(lineIndex -> !map.get(lineIndex).contains(String.valueOf(GALAXY)))
                .boxed()
                .toList();
    }

    private void expandHorizontal() {
        this.map = map.stream()
                .flatMap(line -> line.contains(String.valueOf(GALAXY)) ?
                        Stream.of(line) :
                        Stream.of(line, line))
                .toList();
    }

    private void expandVertical() {
        map = map.stream()
                .map(line -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < line.length(); i++) {
                        stringBuilder.append(line.charAt(i));
                        if (emptyColumns.contains(i)) {
                            stringBuilder.append(line.charAt(i));
                        }
                    }
                    return stringBuilder.toString();
                })
                .toList();
    }

    private void findGalaxies() {
        ArrayList<Coordinates> foundGalaxies = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            String line = map.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == GALAXY) {
                    foundGalaxies.add(new Coordinates(i, j));
                }
            }
        }
        this.galaxies = foundGalaxies;
    }
}
