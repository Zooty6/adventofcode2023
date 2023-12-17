package dev.zooty.day13;

import java.util.List;
import java.util.stream.IntStream;

public class LavaPattern {
    private final List<String> pattern;
    private final List<String> patternTurned;
    public LavaPattern(List<String> input) {
        this.pattern = input;
        this.patternTurned = IntStream.range(0, pattern.getFirst().length())
                        .mapToObj(columnIndex -> pattern.stream().map(s -> String.valueOf(s.charAt(columnIndex)))
                                .reduce("", String::concat))
                        .toList();
    }

    public int summarize(int smudgeAmount) {
        return getMirrorLength(pattern, smudgeAmount) + 100 * getMirrorLength(patternTurned, smudgeAmount);
    }

    private int getMirrorLength(List<String> lines, int smudgeAmount) {
        return IntStream.range(1, lines.getFirst().length())
                .filter(index -> getMirrorErrors(lines, index) == smudgeAmount)
                .sum();
    }

    /**
     * @deprecated old way to do this. Use getMirrorErrors() == 0 instead.
     */
    @Deprecated(since = "1")
    private boolean isMirror(List<String> lines, int rightIterator) {
        int leftIterator = rightIterator - 1;
        while (leftIterator >= 0 && rightIterator < lines.getFirst().length()) {
            int finalLeftIterator = leftIterator;
            int finalRightIterator = rightIterator;
            if (lines.stream()
                    .anyMatch(line -> line.charAt(finalLeftIterator) != line.charAt(finalRightIterator))) {
                return false;
            }
            leftIterator--;
            rightIterator++;
        }
        return true;
    }

    private long getMirrorErrors(List<String> lines, int rightIterator) {
        int leftIterator = rightIterator - 1;
        long errors = 0;
        while (leftIterator >= 0 && rightIterator < lines.getFirst().length()) {
            int finalLeftIterator = leftIterator;
            int finalRightIterator = rightIterator;
            errors += lines.stream()
                    .filter(line -> line.charAt(finalLeftIterator) != line.charAt(finalRightIterator))
                    .count();
            leftIterator--;
            rightIterator++;
        }
        return errors;
    }
}
