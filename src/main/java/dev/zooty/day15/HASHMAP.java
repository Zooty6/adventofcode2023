package dev.zooty.day15;

import java.util.*;
import java.util.stream.IntStream;

public class HASHMAP {
    private final List<Step> steps;
    private final Set<Box> boxes = new HashSet<>();

    public HASHMAP(String input) {
        steps = Arrays.stream(input.split(","))
                .map(Step::new)
                .toList();
    }

    public void configure() {
        steps.forEach(this::doStep);
    }

    public int getSumOfResults() {
        return steps.stream()
                .mapToInt(Step::hashCode)
                .sum();
    }

    public int calculatePower() {
        return boxes.stream()
                .flatMapToInt(box -> IntStream.range(0, box.getLenses().size())
                        .map(lensIndex -> (box.getIndex() + 1) * (lensIndex + 1) * box.getLenses().get(lensIndex).getFocalLength()))
                .sum();
    }

    private void doStep(Step step) {
        if (step.getOperation() == Step.Operation.EQUALS) {
            addLens(step.getLabelHash(), step.getLabel(), step.getFocalLength());
        } else {
            removeLens(step.getLabelHash(), step.getLabel());
        }
    }

    private void addLens(int labelHash, String label, Integer focalLength) {
        boxes.stream()
                .filter(box -> box.getIndex() == labelHash)
                .findAny()
                .ifPresentOrElse(box -> box.getLenses()
                                .stream()
                                .filter(lens -> lens.getLabel().equals(label))
                                .findAny()
                                .ifPresentOrElse(lens -> lens.setFocalLength(focalLength),
                                        () -> box.getLenses().add(new Lens(label, focalLength))),
                        () -> boxes.add(new Box(labelHash, new Lens(label, focalLength))));
    }

    private void removeLens(int labelHash, String label) {
        boxes.stream()
                .filter(box -> box.getIndex() == labelHash)
                .findAny()
                .ifPresent(box -> box.getLenses()
                        .removeIf(lens -> lens.getLabel().equals(label)));
    }
}
