package dev.zooty.day8;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class MapNode {
    private final String label;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MapNode rightNode;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MapNode leftNode;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Boolean isEnd = null;

    public MapNode(String label) {
        this.label = label;
    }

    public boolean isEnd() {
        if (isEnd == null) {
            isEnd = label.endsWith("Z");
        }
        return isEnd;
    }
}
