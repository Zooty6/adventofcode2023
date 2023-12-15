package dev.zooty.day12;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SpringConditionMap {
    private final List<SpringConditionData> rows;

    public SpringConditionMap(BufferedReader rows) {
        this.rows = rows
                .lines()
                .map(SpringConditionData::new)
                .toList();
    }

    public long getArrangements() {
        return rows.stream()
                .mapToLong(this::getArrangementsOfRow)
                .sum();
    }

    private long getArrangementsOfRow(SpringConditionData line) {
        String row = line.getRow();
        ArrayList<String> allPossibleLines = new ArrayList<>();
        getAllPossibleLines(allPossibleLines, row);
        return allPossibleLines.parallelStream()
                .filter(possibleLine -> isPossibleToPlaceGroups(possibleLine, line.getDamagedGroups()))
                .count();
    }

    private boolean isPossibleToPlaceGroups(String lineInQuestion, List<Integer> damagedGroups) {
        lineInQuestion = "." + lineInQuestion + ".";
        for (String aDamagedGroup : damagedGroups.stream()
                .map(lengthOfGroup -> "." + new String(new char[lengthOfGroup]).replace('\0', '#') + ".")
                .toList()) {
            if (lineInQuestion.contains(aDamagedGroup) && lineInQuestion.indexOf(aDamagedGroup) == lineInQuestion.indexOf(".#")) {
                lineInQuestion = lineInQuestion.replaceFirst(aDamagedGroup.replace(".", "\\."), "..");
            } else {
                return false;
            }
        }
        return !lineInQuestion.contains("#");
    }

    private void getAllPossibleLines(List<String> lines, String line) {
        if (line.contains("?")) {
            getAllPossibleLines(lines, line.replaceFirst("\\?", "#"));
            getAllPossibleLines(lines, line.replaceFirst("\\?", "."));
        } else {
            lines.add(line);
        }
    }

    public void unfold(int amount) {
        IntStream.range(0, amount)
                .parallel()
                .forEach(i -> rows.forEach(springConditionData -> springConditionData.unfold(amount)));
    }
}
