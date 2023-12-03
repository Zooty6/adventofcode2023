package dev.zooty.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class EngineSchematic {
    private final static String numberRegex = "\\d+";
    private final static Pattern numberPattern = Pattern.compile(numberRegex);
    private final static String gearRegex = "\\*+";
    private final static Pattern gearPattern = Pattern.compile(gearRegex);
    private final String[] map;

    public EngineSchematic(String schematicMap) {
        map = schematicMap.split("\n");
    }

    public List<Integer> getPartNumbers() {
        ArrayList<Integer> partNumbers = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < map.length; lineNumber++) {
            String line = map[lineNumber];
            for (MatchResult matchResult : numberPattern.matcher(line).results().toList()) {
                if (IntStream.range(lineNumber - 1, lineNumber + 2)
                        .anyMatch(subLine -> hasSymbol(subLine, matchResult.start(), matchResult.end()))) {
                    partNumbers.add(Integer.valueOf(matchResult.group()));
                }
            }
        }
        return partNumbers;
    }

    public List<Integer> getGearRations() {
        List<Integer> gearRations = new ArrayList<>();
        for (int lineNumber = 0; lineNumber < map.length; lineNumber++) {
            String line = map[lineNumber];
            for (MatchResult gearResult : gearPattern.matcher(line).results().toList()) {
                List<Integer> gearParts = getGearParts(lineNumber, gearResult.start());
                if (gearParts.size() == 2) {
                    gearRations.add(gearParts.stream()
                            .reduce(1, Math::multiplyExact));
                }
            }
        }
        return gearRations;
    }

    private List<Integer> getGearParts(int lineNumber, int gearAt) {
        return IntStream.range(lineNumber - 1, lineNumber + 2)
                .flatMap(lineIterator -> getGearPartAtLine(lineIterator, gearAt))
                .boxed()
                .toList();
    }

    private IntStream getGearPartAtLine(int lineNumber, int gearAt) {
        IntStream.Builder gearParts = IntStream.builder();
        if (lineNumber >= 0 && lineNumber < map.length) {
            numberPattern.matcher(map[lineNumber]).results().forEach(matchResult -> {
                if (matchResult.start() <= gearAt + 1 && matchResult.end() - 1 >= gearAt - 1) {
                    gearParts.add(Integer.parseInt(matchResult.group()));
                }
            });
        }
        return gearParts.build();
    }

    private boolean hasSymbol(int lineNumber, int start, int end) {
        if (lineNumber < 0 || lineNumber >= map.length) return false;
        for (int i = Math.max(0, start - 1); i <= Math.min(map[lineNumber].length() - 1, end); i++) {
            char charAtI = map[lineNumber].charAt(i);
            if (!Character.isDigit(charAtI) && charAtI != '.') {
                return true;
            }
        }
        return false;
    }
}
