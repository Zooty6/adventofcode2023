package dev.zooty.day8;

import dev.zooty.day8.DirectionNode.Direction;

import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Map {
    private static final String MAP_REGEX = "(?<label>.*)\\s=\\s\\((?<left>.*),\\s(?<right>.*)\\)";
    private static final Pattern mapPattern = Pattern.compile(MAP_REGEX);
    private final DirectionNode startingDirection;
    private final Set<MapNode> mapNodes;

    public Map(String input) {
        String firstLine = input.lines().findFirst().orElseThrow();
        startingDirection = new DirectionNode(Direction.of(firstLine.charAt(0)));
        createDirectionChain(firstLine);
        mapNodes = input.lines()
                .skip(2)
                .map(line -> mapPattern.matcher(line).results().findAny().orElseThrow().group("label"))
                .map(MapNode::new)
                .collect(Collectors.toSet());
        input.lines()
                .skip(2)
                .forEach(line -> {
                    Matcher matcher = mapPattern.matcher(line);
                    MatchResult matchResult = matcher.results().findAny().orElseThrow();
                    MapNode mapNode = getMapNodeByLabel(matchResult.group("label"));
                    mapNode.setRightNode(getMapNodeByLabel(matchResult.group("right")));
                    mapNode.setLeftNode(getMapNodeByLabel(matchResult.group("left")));
                });
    }

    public int getMinStepsToZ() {
        int steps = 0;
        MapNode currentMapNode = getMapNodeByLabel("AAA");
        MapNode endMapNode = getMapNodeByLabel("ZZZ");
        DirectionNode currentDirection = startingDirection;
        while (!currentMapNode.equals(endMapNode)) {
            currentMapNode = currentDirection.getDirection() == Direction.LEFT ?
                    currentMapNode.getLeftNode() :
                    currentMapNode.getRightNode();
            currentDirection = currentDirection.getNextDirection();
            steps++;
        }
        return steps;
    }

    public long getMinStepsToZForAllA() {
        List<MapNodeIterator> steppingNodes = mapNodes.parallelStream()
                .filter(mapNode -> mapNode.getLabel().endsWith("A"))
                .map(MapNodeIterator::new)
                .toList();
        List<MapNodeModInfo> steppingNodeInfos = steppingNodes.stream().map(mapNodeIterator -> new MapNodeModInfo(mapNodeIterator.getMapNode(), startingDirection)).toList();
        return steppingNodeInfos.stream().mapToLong(MapNodeModInfo::getCycleLength).reduce(1, this::getSmallestMultiply);
    }

    private long getSmallestMultiply(long numA, long numB) {
        long result = numA;
        while (result % numB != 0) {
            result += numA;
        }
        return result;
    }

    private void createDirectionChain(String input) {
        final DirectionNode[] iterator = {startingDirection}; // Java why?
        input.chars().skip(1).forEach(value -> {
            iterator[0].setNextDirection(new DirectionNode(Direction.of((char) value)));
            iterator[0] = iterator[0].getNextDirection();
        });
        iterator[0].setNextDirection(startingDirection);
    }

    private MapNode getMapNodeByLabel(String label) {
        return mapNodes.stream()
                .filter(mapNode -> mapNode.getLabel().equals(label))
                .findAny()
                .orElseThrow();
    }
}
