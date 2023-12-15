package dev.zooty.day12;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class SpringConditionData {
    private String row;
    private List<Integer> damagedGroups;

    public SpringConditionData(String line) {
        String[] split = line.split("\\s");
        row = split[0];
        damagedGroups = Arrays.stream(split[1].split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public void unfold(int times) {
        StringBuilder baseRow = new StringBuilder(row);
        List<Integer> collectingList = new ArrayList<>(damagedGroups);
        for (int i = 0; i < times; i++) {
            baseRow.append("?").append(baseRow);
            collectingList = Stream.concat(collectingList.stream(),damagedGroups.stream()).toList();
        }
        row = baseRow.toString();
        damagedGroups = collectingList;
    }
}
