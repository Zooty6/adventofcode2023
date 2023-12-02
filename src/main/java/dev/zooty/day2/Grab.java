package dev.zooty.day2;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
public class Grab {
    private final List<HomogeneousCubeSet> cubeSet;
    public Grab(String line) {
        cubeSet = Arrays.stream(line.split(", "))
                .map(HomogeneousCubeSet::new)
                .toList();
    }

    public boolean hasMore(Collection<HomogeneousCubeSet> thenThoseCubeSetList) {
        return thenThoseCubeSetList.parallelStream()
                .anyMatch(thenThoseCubeSet -> cubeSet
                        .parallelStream()
                        .filter(grabCubeSet -> grabCubeSet.getColor().equals(thenThoseCubeSet.getColor()))
                        .map(HomogeneousCubeSet::getAmount)
                        .findAny()
                        .orElse(0) > thenThoseCubeSet.getAmount());
    }
}
