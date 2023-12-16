package dev.zooty.day13;

import java.util.List;
import java.util.stream.IntStream;

public class LavaPattern {
    private final List<String> pattern;
    public LavaPattern(List<String> input) {
        this.pattern = input;
    }

    public int summarize() {
        return verticalSum() + 400 * horizontalSum();
    }


    private int verticalSum() {
        return IntStream.range(0, pattern.getFirst().length())
                .filter(this::isVerticalMirror)
                .sum();
    }

    private boolean isVerticalMirror(int i) {
        return false;
    }

    private int horizontalSum() {
        return IntStream.range(0, pattern.size())
                .filter(this::isHorizontalMirror)
                .sum();
    }

    private boolean isHorizontalMirror(int i) {
        return false;
    }
}
