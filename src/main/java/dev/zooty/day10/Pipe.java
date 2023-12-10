package dev.zooty.day10;

import lombok.Data;
import lombok.ToString;

@Data
public final class Pipe {
    @ToString.Exclude
    private Pipe before;
    @ToString.Exclude
    private Pipe after;
    private final Coordinates coordinates;
    private final char pipeChar;
    private final int distance;
}
