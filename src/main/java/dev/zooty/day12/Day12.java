package dev.zooty.day12;

import dev.zooty.Day;
import dev.zooty.Ignored;
import lombok.Getter;

@Getter
public class Day12 implements Day {
    private final int day = 12;

    @Override
    public String getSolution1() {
        return String.valueOf(new SpringConditionMap(getInputReader())
                .getArrangements());
    }

    @Override
    @Ignored(reason = "This takes too long to run and heap size needs to be increased")
    public String getSolution2() {
        SpringConditionMap springConditionMap = new SpringConditionMap(getInputReader());
        springConditionMap.unfold(5);
        return String.valueOf(springConditionMap.getArrangements());
    }
}
