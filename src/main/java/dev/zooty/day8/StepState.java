package dev.zooty.day8;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public final class StepState {
    private final MapNode mapNode;
    private final DirectionNode directionNode;
    @EqualsAndHashCode.Exclude
    private final int steps;
}
