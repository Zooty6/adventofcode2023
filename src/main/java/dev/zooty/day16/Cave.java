package dev.zooty.day16;

import java.awt.*;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cave {
    static final char MIRROR1 = '/';
    static final char MIRROR2 = '\\';
    static final char SPLITTER_HORIZONTAL = '-';
    static final char SPLITTER_VERTICAL = '|';
    private final List<String> map;
    private final Set<Tile> visitedTiles = new HashSet<>();
    private final Set<Light> lights = new HashSet<>();

    public Cave(BufferedReader map) {
        this.map = map.lines().toList();
    }

    public int countEnergizedTiles() {
        return countEnergizedTiles(new Light(new Point(0, 0), Light.Direction.EAST));
    }

    public int countEnergizedTilesFromAllDirection() {
        int maxEnergy = 0;
        for (int i = 0; i < map.getFirst().length(); i++) {
            maxEnergy = Math.max(maxEnergy, countEnergizedTiles(new Light(new Point(0, i), Light.Direction.SOUTH)));
            maxEnergy = Math.max(maxEnergy, countEnergizedTiles(new Light(new Point(map.size() - 1, i), Light.Direction.NORTH)));
        }
        for (int i = 0; i < map.size(); i++) {
            maxEnergy = Math.max(maxEnergy, countEnergizedTiles(new Light(new Point(i, 0), Light.Direction.EAST)));
            maxEnergy = Math.max(maxEnergy, countEnergizedTiles(new Light(new Point(i, map.getFirst().length() - 1), Light.Direction.WEST)));
        }
        return maxEnergy;
    }

    private int countEnergizedTiles(Light startingLight) {
        visitedTiles.clear();
        initStartLight(startingLight);
        while (!lights.isEmpty()) {
            handleLights();
        }
        return visitedTiles.size();
    }

    private void handleLights() {
        move();
        lights.removeIf(this::isOutOfMap);
        processObstacles();
        lights.removeIf(this::isVisitedFromThisDirection);
        visit();
    }

    private void move() {
        lights.parallelStream().forEach(Light::move);
    }

    private boolean isVisitedFromThisDirection(Light light) {
        return visitedTiles.stream()
                .filter(tile -> tile.getPosition().equals(light.getPosition()))
                .anyMatch(tile -> tile.getVisitedDirections().contains(light.getDirection()));
    }

    private boolean isOutOfMap(Light light) {
        Point position = light.getPosition();
        return position.x < 0 || position.x >= map.size() || position.y < 0 || position.y >= map.get(position.x).length();
    }

    private void processObstacles() {
        HashSet<Light> toAddLights = new HashSet<>();
        lights.parallelStream().forEach(light -> {
            char charAtLight = getCharacterAt(light.getPosition());
            if (charAtLight == MIRROR1 || charAtLight == MIRROR2) {
                light.bounce(charAtLight);
            } else if (charAtLight == SPLITTER_HORIZONTAL || charAtLight == SPLITTER_VERTICAL) {
                light.split(charAtLight).ifPresent(toAddLights::add);
            }
        });
        lights.addAll(toAddLights);
    }

    private void visit() {
        lights.forEach(light -> visitedTiles
                .parallelStream()
                .filter(tile -> tile.getPosition().equals(light.getPosition()))
                .findAny()
                .ifPresentOrElse(tile -> tile.visit(light),
                        () -> visitedTiles.add(new Tile(light))));
    }

    private char getCharacterAt(Point position) {
        return map.get(position.x).charAt(position.y);
    }

    private void initStartLight(Light initLight) {
        lights.add(initLight);
        processObstacles();
        visitedTiles.add(new Tile(initLight));
    }
}
