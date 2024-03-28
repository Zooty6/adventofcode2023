package dev.zooty.day25;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
public class Component implements Comparable<Component> {
    @EqualsAndHashCode.Include
    private final String label;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<Component> connectedComponents;

    public Component(String label) {
        this.label = label;
        this.connectedComponents = new HashSet<>();
    }

    @Override
    public int compareTo(Component other) {
        return label.compareTo(other.getLabel());
    }
}
