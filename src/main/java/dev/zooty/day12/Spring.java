package dev.zooty.day12;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public class Spring {
    private final SpringCondition measuredCondition;
    @Setter
    private SpringCondition calculatedCondition;

    public Spring(char character) {
        this.measuredCondition = SpringCondition.of(character);
        this.calculatedCondition = this.measuredCondition;
    }

    public enum SpringCondition {
        OPERATIONAL('.'),
        DAMAGED('#'),
        UNKNOWN('?');
        private final char value;

        SpringCondition(char value) {
            this.value = value;
        }

        public static SpringCondition of(char character) {
            return Arrays.stream(SpringCondition.values())
                    .parallel()
                    .filter(condition -> condition.value == character)
                    .findAny()
                    .orElseThrow();
        }
    }
}
