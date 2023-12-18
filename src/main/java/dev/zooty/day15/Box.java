package dev.zooty.day15;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Box {
    @EqualsAndHashCode.Include
    private final int index;
    private final List<Lens> lenses = new ArrayList<>();

    public Box(int index, Lens lens) {
        this.index = index;
        this.lenses.add(lens);
    }
}
