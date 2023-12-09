package dev.zooty.day8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
@AllArgsConstructor
public class MapNodeIterator {
    private MapNode mapNode;

    @SneakyThrows
    public void next(DirectionNode.Direction direction) {
        switch (direction) {
            case LEFT -> mapNode = mapNode.getLeftNode();
            case RIGHT -> mapNode = mapNode.getRightNode();
            default -> throw new IllegalAccessException();
        }
    }
}
