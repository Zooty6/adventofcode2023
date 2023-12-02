package dev.zooty.day2;


import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Getter
public final class Game {
    private static final String regex = "Game\\s(?<id>\\d+):\\s(?<data>.+)";
    private static final Pattern pattern = Pattern.compile(regex);
    private final int id;
    private final List<Grab> grabs;

    public Game(String line) {
        MatchResult matcher = pattern.matcher(line)
                .results()
                .findAny()
                .orElseThrow();
        id = Integer.parseInt(matcher.group("id"));
        grabs = Arrays.stream(matcher.group("data").split("; "))
                .map(Grab::new).toList();
    }

    public boolean hasAtLeast(Collection<HomogeneousCubeSet> cubes) {
        return grabs.parallelStream()
                .noneMatch(grab -> grab.hasMore(cubes));
    }

    public int power() {
        return Arrays.stream(Color.values())
                .map(color -> new HomogeneousCubeSet(color, grabs.stream()
                        .mapToInt(aGrab -> aGrab.getCubeSet()
                                .parallelStream()
                                .filter(cubeSet -> cubeSet.getColor().equals(color))
                                .findAny()
                                .map(HomogeneousCubeSet::getAmount)
                                .orElse(0))
                        .max()
                        .orElse(0)))
                .mapToInt(HomogeneousCubeSet::getAmount)
                .reduce(1, Math::multiplyExact);
    }
}
