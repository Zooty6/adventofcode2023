package dev.zooty.day8;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class DirectionNode {
    private final Direction direction;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DirectionNode nextDirection;

    @AllArgsConstructor
    public enum Direction {
        left('L'),
        right('R');
        private final char value;

        public static Direction of(char character) {
            return Arrays.stream(Direction.values())
                    .filter(direction -> direction.value == character)
                    .findAny()
                    .orElseThrow();
        }
    }
}
