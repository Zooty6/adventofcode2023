package dev.zooty.day10;

import lombok.Data;

import java.util.*;

import static dev.zooty.day10.Coordinates.Direction.UP;

public class PipeMap {

    private static final Character HORIZONTAL_PIPE = '-';
    private static final Character VERTICAL_PIPE = '|';
    private static final Character NORTH_EAST_PIPE = 'L';
    private static final Character NORTH_WEST_PIPE = 'J';
    private static final Character SOUTH_EAST_PIPE = 'F';
    private static final Character SOUTH_WEST_PIPE = '7';
    private static final Character START_PIPE = 'S';
    private static final Character TILE = '.';
    private static final Character FILLER = 'o';
    private static final Character EMPTY = 'x';
    private static final Map<Character, Set<Coordinates.Direction>> characterDirections = Map.of(
            VERTICAL_PIPE, Set.of(UP, Coordinates.Direction.DOWN),
            HORIZONTAL_PIPE, Set.of(Coordinates.Direction.LEFT, Coordinates.Direction.RIGHT),
            NORTH_EAST_PIPE, Set.of(UP, Coordinates.Direction.RIGHT),
            NORTH_WEST_PIPE, Set.of(UP, Coordinates.Direction.LEFT),
            SOUTH_EAST_PIPE, Set.of(Coordinates.Direction.DOWN, Coordinates.Direction.RIGHT),
            SOUTH_WEST_PIPE, Set.of(Coordinates.Direction.DOWN, Coordinates.Direction.LEFT),
            START_PIPE, Set.of(UP, Coordinates.Direction.DOWN, Coordinates.Direction.LEFT, Coordinates.Direction.RIGHT)
    );

    private final Pipe startPipe;
    private List<String> mapLines;
    private final Set<Coordinates> visitedCoordinates = new HashSet<>();

    public PipeMap(String map) {

        mapLines = map.lines().toList();
        Coordinates startingCoordinates = findStartingCoordinates(mapLines);
        startPipe = new Pipe(startingCoordinates, START_PIPE, 0);
        Coordinates aWay = findDirections(startPipe).getFirst();
        startPipe.setAfter(new Pipe(aWay, getCharAtCoordinates(aWay), 1));
        startPipe.getAfter().setBefore(startPipe);
        traversePipe(startPipe.getAfter(), 2);
    }

    public int getMaxDistance() {
        return Math.round((float) startPipe.getBefore().getDistance() / 2);
    }

    public int getEnclosedTiles() {
        expand();
        ArrayList<Coordinates> foundEnclosedTiles = new ArrayList<>();
        for (int i = 0; i < mapLines.size(); i++) {
            String line = mapLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char character = line.charAt(j);
                Coordinates visitingCoordinate = new Coordinates(i, j);
                if (character == TILE && !visitedCoordinates.contains(visitingCoordinate)) {
                    visitedCoordinates.add(visitingCoordinate);
                    Enclosure enclosedTiles = findEnclosedTiles(visitingCoordinate);
                    if (enclosedTiles.isEnclosed()) {
                        foundEnclosedTiles.addAll(enclosedTiles.getEnclosedCoordinates());
                    }
                }
            }
        }
        return foundEnclosedTiles.size();
    }

    private boolean isEnclosed(Coordinates coordinates) {
        char character = getCharAtCoordinates(coordinates);
        return (character == TILE || character == FILLER) && !visitedCoordinates.contains(coordinates);
    }

    private boolean inMainLoop(Coordinates coordinates) {
        Pipe iterator = startPipe;
         do {
            if (iterator.getCoordinates().equals(coordinates)) {
                return true;
            }
            iterator = iterator.getAfter();
        } while (iterator != startPipe);
        return false;
    }

    private Enclosure findEnclosedTiles(Coordinates coordinates) {
        Enclosure enclosedCoordinates = new Enclosure();

        if (getCharAtCoordinates(coordinates) != FILLER) {
            enclosedCoordinates.getEnclosedCoordinates().add(coordinates);
        }

        if (Arrays.stream(Coordinates.Direction.values())
                .map(coordinates::of)
                .map(this::getCharAtCoordinates)
                .anyMatch(character -> Objects.equals(character, EMPTY))) {
            enclosedCoordinates.setEnclosed(false);
        }
        Arrays.stream(Coordinates.Direction.values())
                .map(coordinates::of)
                .filter(this::isEnclosed)
                .forEach(toCoordinate -> {
                    visitedCoordinates.add(toCoordinate);
                    Enclosure enclosure = findEnclosedTiles(toCoordinate);
                    if (!enclosure.isEnclosed()) {
                        enclosedCoordinates.setEnclosed(false);
                    }
                    enclosedCoordinates.getEnclosedCoordinates().addAll(enclosure.getEnclosedCoordinates());
                });
        return enclosedCoordinates;
    }

    @Data
    private static class Enclosure {
        boolean isEnclosed = true;
        List<Coordinates> enclosedCoordinates = new ArrayList<>();
    }

    private void expand() {
        ArrayList<String> newMap = new ArrayList<>();
        for (int i = 0; i < mapLines.size(); i++) {
            StringBuilder originalLineBuilder = new StringBuilder();
            StringBuilder newLineBuilder = new StringBuilder();
            String line = mapLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char thisChar = line.charAt(j);
                char left = j == 0 ? TILE : mapLines.get(i).charAt(j - 1);
                if (j != 0) {
                    originalLineBuilder.append(Set.of(HORIZONTAL_PIPE, SOUTH_EAST_PIPE, NORTH_EAST_PIPE, START_PIPE).contains(left) ? HORIZONTAL_PIPE : FILLER);
                }

                originalLineBuilder.append(thisChar == TILE || !inMainLoop(new Coordinates(i, j)) ? TILE : thisChar);
                if (i != 0) {
                    char above = mapLines.get(i - 1).charAt(j);
                    newLineBuilder.append(Set.of(VERTICAL_PIPE, SOUTH_EAST_PIPE, SOUTH_WEST_PIPE, START_PIPE).contains(above) ? VERTICAL_PIPE : FILLER);
                    if (j < line.length() - 1) {
                        newLineBuilder.append(FILLER);
                    }
                }
            }
            if (!newLineBuilder.isEmpty()) { newMap.add(newLineBuilder.toString()); }
            newMap.add(originalLineBuilder.toString());
        }
        this.mapLines = newMap;
    }

    private void traversePipe(Pipe current, int startingStep) {
        while (true) {
            List<Coordinates> directions = findDirections(current);
            Pipe finalCurrent = current;
            Coordinates nextCoordinate = directions
                    .stream()
                    .filter(coordinates -> !coordinates.equals(finalCurrent.getBefore().getCoordinates()))
                    .findAny()
                    .orElseThrow();
            if (!nextCoordinate.equals(startPipe.getCoordinates())) {
                current.setAfter(new Pipe(nextCoordinate, getCharAtCoordinates(nextCoordinate), startingStep));
                current.getAfter().setBefore(current);
                startingStep = startingStep + 1;
                current = current.getAfter();
            } else {
                current.setAfter(startPipe);
                startPipe.setBefore(current);
                break;
            }
        }
    }

    private Coordinates findStartingCoordinates(List<String> mapLines) {
        for (int i = 0; i < mapLines.size(); i++) {
            String line = mapLines.get(i);
            if (line.contains(START_PIPE.toString())) {
                return new Coordinates(i, findStartingCoordinates(line));
            }
        }
        throw new NoSuchElementException("No starting coordinates found");
    }

    private List<Coordinates> findDirections(Pipe pipe) {
        return characterDirections
                .get(pipe.getPipeChar()).stream()
                .map(pipe.getCoordinates()::of)
                .filter(coordinates -> {
                    char charAt = getCharAtCoordinates(coordinates);
                    return characterDirections.containsKey(charAt) && characterDirections.get(charAt).stream()
                            .anyMatch(direction -> coordinates.of(direction).equals(pipe.getCoordinates()));
                }).toList();
    }

    private char getCharAtCoordinates(Coordinates coordinates) {
        try {
            return mapLines.get(coordinates.x()).charAt(coordinates.y());
        } catch (IndexOutOfBoundsException e) {
            return 'x';
        }
    }

    private int findStartingCoordinates(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == START_PIPE) {
                return i;
            }
        }
        return -1;
    }
}
