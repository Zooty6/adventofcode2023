package dev.zooty.day25;

import org.javatuples.Pair;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ComponentPool {

    private static final String LINE_REGEX = "^(?<nodeLabel>.*):\\s(?<connectedNodes>.*)$";
    private final Set<Component> components = new HashSet<>();

    public ComponentPool(BufferedReader reader) {
        reader.lines()
                .map(line -> Pattern.compile(LINE_REGEX)
                        .matcher(line)
                        .results()
                        .findAny()
                        .orElseThrow())
                .map(matchResult -> new Pair<>(
                        matchResult.group("nodeLabel"),
                        matchResult.group("connectedNodes").split("\\s")))
                .flatMap(pair -> Arrays.stream(pair.getValue1())
                        .map(connectedLabel -> new Pair<>(getOrCreateComponent(pair.getValue0()), connectedLabel)))
                .forEach(pair -> {
                    var component = getOrCreateComponent(pair.getValue1());
                    pair.getValue0().getConnectedComponents().add(component);
                    component.getConnectedComponents().add(pair.getValue0());
                });
    }

    public long getDividedGroupSize() {
        var connectionList = createConnectionSet(components).stream().toList();
        return LongStream.range(0, connectionList.size())
                .flatMap(index1 -> LongStream.range(0, connectionList.size())
                        .filter(index2 -> index1 != index2)
                        .flatMap(index2 -> LongStream.range(0, connectionList.size())
                                .filter(index3 -> index3 != index1 && index3 != index2)
                                .map(index3 -> getDividedGroupSiteAfterRemoving(
                                        List.of(
                                                connectionList.get((int) index1),
                                                connectionList.get((int) index2),
                                                connectionList.get((int) index3)
                                        )))))
                .filter(value -> value > 0)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can't find good cut"));
    }

    private long getDividedGroupSiteAfterRemoving(List<Connection> connectionsToRemove) {
        removeConnection(connectionsToRemove);
        var dividedPair = connectionsToRemove.stream()
                .filter(connection -> !canReach(connection.component1(), connection.component2()))
                .findAny();
        if (dividedPair.isPresent()) {
            return calculateDividedGroups(dividedPair.get());
        }
        addConnections(connectionsToRemove);
        return 0;
    }

    private long calculateDividedGroups(Connection connection) {
        var sizeOfAGroup = components.stream()
                .filter(component -> !component.equals(connection.component1()))
                .filter(component -> canReach(connection.component1(), component))
                .count();
        return sizeOfAGroup * (components.size() - sizeOfAGroup);
    }

    private void addConnections(List<Connection> connectionsToRemove) {
        connectionsToRemove.forEach(connection -> {
            connection.component1().getConnectedComponents().add(connection.component2());
            connection.component2().getConnectedComponents().add(connection.component1());
        });
    }

    private void removeConnection(List<Connection> connectionsToRemove) {
        connectionsToRemove.forEach(connection -> {
            connection.component1().getConnectedComponents().remove(connection.component2());
            connection.component2().getConnectedComponents().remove(connection.component1());
        });
    }

    private Set<Connection> createConnectionSet(Set<Component> components) {
        return components.stream()
                .flatMap(component -> component.getConnectedComponents()
                        .stream()
                        .map(connectedComponent -> new Connection(component, connectedComponent)))
                .collect(Collectors.toSet());
    }

    private Component getOrCreateComponent(String label) {
        var component = components.stream()
                .filter(component1 -> component1.getLabel().equals(label))
                .findAny()
                .orElseGet(() -> new Component(label));
        components.add(component);
        return component;
    }

    private boolean canReach(Component from, Component to) {
        return canReach(from, to, components.size(), new HashSet<>());
    }

    private boolean canReach(Component from, Component to, int depth, Set<Component> visited) {
        if (from.equals(to)) {
            return true;
        }
        if (depth < 1) {
            return false;
        }
        visited.add(from);
        return from.getConnectedComponents()
                .parallelStream()
                .filter(component -> !visited.contains(component))
                .anyMatch(component -> canReach(component, to, depth - 1, visited));
    }
}
