package dev.zooty.day8;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
public class MapNodeModInfo {
    private final int cycleLength;
    private final int exitLength;
    private final String startingLabel;
    private int stepsToGetInCycle;

    public MapNodeModInfo(MapNode mapNode, DirectionNode startingDirection) {
        this.startingLabel = mapNode.getLabel();
        this.cycleLength = getCycleLength(mapNode, startingDirection);
        this.exitLength = getStepsTillEnd(mapNode, this.cycleLength, startingDirection);
    }

    private int getCycleLength(MapNode node, DirectionNode startingDirection) {
        MapNodeIterator nodeIterator = new MapNodeIterator(node);
        DirectionNode currentDirection = startingDirection;
        Set<StepState> previousStepStates = new HashSet<>();
        int steps = 0;
        while (!previousStepStates.contains(new StepState(nodeIterator.getMapNode(), currentDirection, steps))) {
            previousStepStates.add(new StepState(nodeIterator.getMapNode(), currentDirection, steps));
            nodeIterator.next(currentDirection.getDirection());
            currentDirection = currentDirection.getNextDirection();
            steps++;
        }
        DirectionNode finalCurrentDirection = currentDirection;
        this.stepsToGetInCycle = previousStepStates.stream()
                .filter(stepState -> stepState.equals(new StepState(nodeIterator.getMapNode(), finalCurrentDirection, 0)))
                .findAny()
                .orElseThrow()
                .getSteps();
        return steps - this.stepsToGetInCycle;
    }

    private int getStepsTillEnd(MapNode node, int cycleLength, DirectionNode startingDirection) {
        MapNodeIterator nodeIterator = new MapNodeIterator(node);
        DirectionNode currentDirection = startingDirection;
        for (int i = -1; i < cycleLength + this.stepsToGetInCycle; i++) {
            if (nodeIterator.getMapNode().isEnd()) {
                return i - this.stepsToGetInCycle;
            }
            nodeIterator.next(currentDirection.getDirection());
            currentDirection = currentDirection.getNextDirection();
        }
        throw new IllegalStateException("Couldn't find end in cycle");
    }
}
