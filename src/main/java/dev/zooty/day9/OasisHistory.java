package dev.zooty.day9;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class OasisHistory {
    private final List<Integer> values;
    private OasisHistory differences = null;
    private Integer extrapolate = null;
    private Integer extrapolateBack = null;

    public OasisHistory(String line) {
        values = Arrays.stream(line.split("\\s"))
                .map(Integer::parseInt)
                .toList();
    }

    private OasisHistory(List<Integer> values) {
        this.values = values;
    }

    public OasisHistory getDifferences() {
        if (listHasNonZeroValue() && this.differences == null) {
            List<Integer> calculatedDiffs = new ArrayList<>();
            for (int i = 1; i < values.size(); i++) {
                calculatedDiffs.add(values.get(i) - values.get(i - 1));
            }
            this.differences = new OasisHistory(calculatedDiffs);
        }
        return this.differences;
    }

    public Integer getExtrapolate() {
        if (this.extrapolate == null) {
            this.extrapolate = listHasNonZeroValue() ? values.getLast() + this.getDifferences().getExtrapolate() : 0;
        }
        return this.extrapolate;
    }

    public Integer getExtrapolateBack() {
        if (this.extrapolateBack == null) {
            this.extrapolateBack = listHasNonZeroValue() ? this.values.getFirst() - this.getDifferences().getExtrapolateBack() : 0;
        }
        return this.extrapolateBack;
    }

    private boolean listHasNonZeroValue() {
        return this.values.parallelStream().anyMatch(integer -> !integer.equals(0));
    }
}
