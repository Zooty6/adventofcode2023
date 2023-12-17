package dev.zooty.day14;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ReflectorDish {
    private static final char EMPTY = '.';
    private static final char ROLLING_ROCK = 'O';
    private final List<String> dishLines;

    public ReflectorDish(BufferedReader reader) {
        dishLines = new ArrayList<>(reader.lines().toList());
    }

    public void tilt() {
        for (int height = 0; height < dishLines.size(); height++) {
            for (int columnIndex = 0; columnIndex < dishLines.getFirst().length(); columnIndex++) {
                char aDishCharacter = dishLines.get(height).charAt(columnIndex);
                if (aDishCharacter == ROLLING_ROCK) {
                    int newPlace = height;
                    while (newPlace - 1 >= 0 && dishLines.get(newPlace - 1).charAt(columnIndex) == EMPTY) {
                        newPlace--;
                    }
                    dishLines.set(height, dishLines.get(height).substring(0, columnIndex) + EMPTY + dishLines.get(height).substring(columnIndex + 1));
                    dishLines.set(newPlace, dishLines.get(newPlace).substring(0, columnIndex) + ROLLING_ROCK + dishLines.get(newPlace).substring(columnIndex + 1));
                }
            }
        }
    }

    public int getTotalLoad() {
        return IntStream.range(0, dishLines.size())
                .map(index -> (dishLines.size() - index) *
                        (dishLines.get(index).length() -
                                dishLines.get(index).replace(String.valueOf(ROLLING_ROCK), "").length()))
                .sum();
    }

    public int balance(int cycles) {
        for (int i = 0; i < cycles; i++) {
            doCycle();
        }
        return getTotalLoad();
    }

    private void doCycle() {
        tilt();
        rotate(); // west
        tilt();
        rotate(); // south
        tilt();
        rotate(); // east
        tilt();
        rotate(); // north
    }


    private void rotate() {
        List<String> newDishLines = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < dishLines.getFirst().length(); columnIndex++) {
            StringBuilder newDishLine = new StringBuilder();
            for (int height = dishLines.size() - 1; height >= 0; height--) {
                newDishLine.append(dishLines.get(height).charAt(columnIndex));
            }
            newDishLines.add(newDishLine.toString());
        }
        dishLines.clear();
        dishLines.addAll(newDishLines);
    }
}
