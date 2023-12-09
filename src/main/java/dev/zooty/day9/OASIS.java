package dev.zooty.day9;

import lombok.Getter;

import java.io.BufferedReader;
import java.util.List;

@Getter
public class OASIS {
    private final List<OasisHistory> histories;

    public OASIS(BufferedReader reader) {
        histories = reader.lines()
                .map(OasisHistory::new)
                .toList();
    }
}
