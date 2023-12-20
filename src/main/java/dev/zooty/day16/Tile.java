package dev.zooty.day16;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tile {
    @EqualsAndHashCode.Include
    @ToString.Include
    private final Point position;
    private final Set<Light.Direction> visitedDirections = new HashSet<>();

    public Tile(Light light) {
        this.position = new Point(light.getPosition());
        visitedDirections.add(light.getDirection());
    }

    public void visit(Light light) {
        visitedDirections.add(light.getDirection());
    }
}
