package dev.zooty.day25;

public record Connection(Component component1, Component component2) {
    public Connection(Component component1, Component component2) {
        this.component1 = component1.compareTo(component2) > 0 ? component1 : component2;
        this.component2 = component1.compareTo(component2) <= 0 ? component1 : component2;
    }
}
