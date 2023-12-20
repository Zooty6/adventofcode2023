package dev.zooty.day16;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

@Getter
@Setter
@ToString
public class Light {
    @EqualsAndHashCode.Include
    private Point position;
    private Direction direction;

    public Light(Point position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public void move() {
        direction.move(position);
    }

    public void bounce(char mirror) {
        this.direction = switch (this.direction) {
            case NORTH -> mirror == Cave.MIRROR1 ? Direction.EAST : Direction.WEST;
            case EAST -> mirror == Cave.MIRROR1 ? Direction.NORTH : Direction.SOUTH;
            case SOUTH -> mirror == Cave.MIRROR1 ? Direction.WEST : Direction.EAST;
            case WEST -> mirror == Cave.MIRROR1 ? Direction.SOUTH : Direction.NORTH;
        };
    }

    public Optional<Light> split(char charAtLight) {
        if (charAtLight == Cave.SPLITTER_HORIZONTAL && (this.direction == Direction.NORTH || this.direction == Direction.SOUTH)) {
            this.direction = Direction.WEST;
            return Optional.of(new Light(new Point(position), Direction.EAST));
        } else  if (charAtLight == Cave.SPLITTER_VERTICAL && (this.direction == Direction.EAST || this.direction == Direction.WEST)) {
            this.direction = Direction.NORTH;
            return Optional.of(new Light(new Point(position), Direction.SOUTH));
        } else {
            return Optional.empty();
        }
    }

    public enum Direction {
        NORTH(point -> point.translate(-1, 0)),
        EAST(point ->  point.translate(0, 1)),
        SOUTH(point ->  point.translate(1, 0)),
        WEST(point ->  point.translate(0, -1));

        private final Consumer<Point> move;

        Direction(Consumer<Point> move) {
            this.move = move;
        }

        public void move(Point point) {
            move.accept(point);
        }

    }
}
