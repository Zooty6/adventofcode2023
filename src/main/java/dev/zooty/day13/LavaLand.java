package dev.zooty.day13;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class LavaLand {
    private final List<LavaPattern> lavaPatterns;
    public LavaLand(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        AtomicReference<Integer> previousIndex = new AtomicReference<>(0);
        lavaPatterns = IntStream.range(0, lines.size())
                .filter(index -> lines.get(index).isEmpty())
                .mapToObj(index -> {
                    List<String> map = lines.subList(previousIndex.get(), index);
                    previousIndex.set(index + 1);
                    return map;
                })
                .map(LavaPattern::new)
                .toList();
    }

    public int summarize() {
        return lavaPatterns.stream()
                .mapToInt(LavaPattern::summarize)
                .sum();
    }

}


