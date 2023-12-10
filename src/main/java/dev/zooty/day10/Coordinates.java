package dev.zooty.day10;

record Coordinates(int x, int y) {
    public Coordinates of(Direction direction) {
        return switch (direction) {
            case UP -> new Coordinates(x - 1, y);
            case DOWN -> new Coordinates(x + 1, y);
            case LEFT -> new Coordinates(x, y - 1);
            case RIGHT -> new Coordinates(x, y + 1);
        };
    }

    public enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT,
    }
}
