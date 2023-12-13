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
    private final List<String> mapLines;

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
        int enclosedTiles = 0;
        for (int x = 0; x < mapLines.size(); x++) {
            String line = mapLines.get(x);
            EnclosureStatus enclosureStatus = new EnclosureStatus();
            for (int y = 0; y < line.length(); y++) {
                char character = line.charAt(y);
                if (inMainLoop(new Coordinates(x, y))) {
                    enclosureStatus.nextState(character);
                } else if (enclosureStatus.isEnclosed()) {
                    enclosedTiles++;
                }
            }
        }
        return enclosedTiles;
    }


    @Data
    private static class EnclosureStatus {
        private boolean isEnclosed = false;
        private Character previousHit = null;

        public void nextState(char character) {
            if (isVertical(character)) {
                if (previousHit == null ||
                        !(previousHit.equals(NORTH_EAST_PIPE) && SOUTH_WEST_PIPE.equals(character) ||
                                previousHit.equals(SOUTH_EAST_PIPE) && NORTH_WEST_PIPE.equals(character))) {
                    isEnclosed = !isEnclosed;
                }
                previousHit = character;
            }
        }

        private boolean isVertical(char character) {
            return character == VERTICAL_PIPE ||
                    character == NORTH_EAST_PIPE ||
                    character == NORTH_WEST_PIPE ||
                    character == SOUTH_EAST_PIPE ||
                    character == SOUTH_WEST_PIPE ||
                    character == START_PIPE;
        }
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
