package dev.zooty.day2;

import dev.zooty.Day;
import lombok.Getter;

import java.util.Set;

@Getter
public class Day2 implements Day {
    private final int day = 2;

    @Override
    public String getSolution1() {
        Set<HomogeneousCubeSet> atLeastGrabs = Set.of(
                new HomogeneousCubeSet(Color.RED, 12),
                new HomogeneousCubeSet(Color.GREEN, 13),
                new HomogeneousCubeSet(Color.BLUE, 14));
        return String.valueOf(getInputReader()
                .lines()
                .map(Game::new)
                .filter(game -> game.hasAtLeast(atLeastGrabs))
                .mapToInt(Game::getId)
                .sum());
    }

    @Override
    public String getSolution2() {
        return String.valueOf(getInputReader()
                .lines()
                .map(Game::new)
                .mapToInt(Game::power)
                .sum());
    }
}
