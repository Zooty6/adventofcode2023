package dev.zooty.day5;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SeedRange {
    @Setter(AccessLevel.PRIVATE)
    private long startId;
    private long range;

    public long getEndId() {
        return startId + range;
    }
}
