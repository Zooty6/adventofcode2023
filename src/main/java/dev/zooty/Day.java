package dev.zooty;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public interface Day {
    int getDay();
    String getSolution1();
    String getSolution2();
    @SneakyThrows
    default String getInputString() {
        return new String(Objects.requireNonNull(
                        this.getClass().getResourceAsStream(String.format("/day%d.txt", getDay())))
                .readAllBytes(), StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
