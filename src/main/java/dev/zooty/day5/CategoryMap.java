package dev.zooty.day5;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class CategoryMap {
    private long sourceStart;
    private long destinationStart;
    private long range;

    public long getSourceEnd() {
        return sourceStart + range;
    }

    public long getDestinationEnd() {
        return destinationStart + range;
    }
}
