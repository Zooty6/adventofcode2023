package dev.zooty.day5;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Almanac {
    private final static String mapsRegex = "%s map:(?<maps>(\\n.+)*)";
    private final static String SEED_TO_SOIL = "seed-to-soil";
    private final static String SOIL_TO_FERTILIZER = "soil-to-fertilizer";
    private final static String FERTILIZER_TO_WATER = "fertilizer-to-water";
    private final static String WATER_TO_LIGHT = "water-to-light";
    private final static String LIGHT_TO_TEMPERATURE = "light-to-temperature";
    private final static String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity";
    private final static String HUMIDITY_TO_LOCATION = "humidity-to-location";
    private final static String seedRegex = "seeds:\\s(?<seeds>.*)";
    private final static Pattern seedPattern = Pattern.compile(seedRegex);
    private final List<SeedRange> seedRanges;
    private final List<CategoryMap> seedToSoilCategoryMap;
    private final List<CategoryMap> soilToFertilizerCategoryMap;
    private final List<CategoryMap> fertilizerToWaterCategoryMap;
    private final List<CategoryMap> waterToLightCategoryMap;
    private final List<CategoryMap> lightToTemperatureCategoryMap;
    private final List<CategoryMap> temperatureToHumidityCategoryMap;
    private final List<CategoryMap> humidityToLocationCategoryMap;

    public Almanac(String input) {
        seedRanges = parseSeeds(input);
        seedToSoilCategoryMap = parseCategoryMap(input, SEED_TO_SOIL);
        soilToFertilizerCategoryMap = parseCategoryMap(input, SOIL_TO_FERTILIZER);
        fertilizerToWaterCategoryMap = parseCategoryMap(input, FERTILIZER_TO_WATER);
        waterToLightCategoryMap = parseCategoryMap(input, WATER_TO_LIGHT);
        lightToTemperatureCategoryMap = parseCategoryMap(input, LIGHT_TO_TEMPERATURE);
        temperatureToHumidityCategoryMap = parseCategoryMap(input, TEMPERATURE_TO_HUMIDITY);
        humidityToLocationCategoryMap = parseCategoryMap(input, HUMIDITY_TO_LOCATION);
    }

    public long getLowestLocationWithRangeAsId() {
        return getLowestLocation(seedRanges.parallelStream()
                .flatMap(seedRange -> Stream.of(seedRange.getStartId(), seedRange.getRange()))
                .mapToLong(Long::longValue));

    }

    public long getLowestLocationWithRanges() {
        long location = 0;
        while (true) {
            long iterator = location;
            iterator = iteratorUpdate(iterator, humidityToLocationCategoryMap);
            iterator = iteratorUpdate(iterator, temperatureToHumidityCategoryMap);
            iterator = iteratorUpdate(iterator, lightToTemperatureCategoryMap);
            iterator = iteratorUpdate(iterator, waterToLightCategoryMap);
            iterator = iteratorUpdate(iterator, fertilizerToWaterCategoryMap);
            iterator = iteratorUpdate(iterator, soilToFertilizerCategoryMap);
            iterator = iteratorUpdate(iterator, seedToSoilCategoryMap);

            for (SeedRange seedRange : seedRanges) {
                if(inRange(iterator, seedRange.getStartId(), seedRange.getEndId())) {
                    return location;
                }
            }
            location++;
        }
    }

    private boolean inRange(long number, long rangeA, long rangeB) {
        long small = Math.min(rangeA, rangeB);
        long big = Math.max(rangeA, rangeB);
        return small <= number && number < big;
    }

    private long getLowestLocation(LongStream seedIds) {
        return seedIds
                .map(id -> mapSeed(seedToSoilCategoryMap, id))
                .map(id -> mapSeed(soilToFertilizerCategoryMap, id))
                .map(id -> mapSeed(fertilizerToWaterCategoryMap, id))
                .map(id -> mapSeed(waterToLightCategoryMap, id))
                .map(id -> mapSeed(lightToTemperatureCategoryMap, id))
                .map(id -> mapSeed(temperatureToHumidityCategoryMap, id))
                .map(id -> mapSeed(humidityToLocationCategoryMap, id))
                .min()
                .orElseThrow();
    }

    private long mapSeed(List<CategoryMap> categoryMaps, long id) {
        return categoryMaps.parallelStream()
                .filter(categoryMap -> categoryMap.getSourceStart() <= id && categoryMap.getSourceStart() + categoryMap.getRange() > id)
                .map(categoryMap -> categoryMap.getDestinationStart() + (id - categoryMap.getSourceStart()))
                .findAny()
                .orElse(id);
    }

    private long iteratorUpdate(long iterator, List<CategoryMap> categoryMaps) {
        for (CategoryMap categoryMap : categoryMaps) {
            if (inRange(iterator, categoryMap.getDestinationStart(), categoryMap.getDestinationEnd())) {
                return categoryMap.getSourceStart() + (iterator - categoryMap.getDestinationStart());
            }
        }
        return iterator;
    }

    private static List<CategoryMap> parseCategoryMap(String input, String category) {
        return Pattern.compile(mapsRegex.formatted(category))
                .matcher(input)
                .results()
                .findAny()
                .orElseThrow()
                .group("maps")
                .lines()
                .filter(s -> !s.isEmpty())
                .map(line -> {
                    String[] split = line.split("\\s");
                    return new CategoryMap(
                            Long.parseLong(split[1]),
                            Long.parseLong(split[0]),
                            Long.parseLong(split[2]));
                })
                .toList();
    }

    private static List<SeedRange> parseSeeds(String input) {
        return Pattern.compile("(?<start>\\d+)\\s(?<range>\\d+)")
                .matcher(seedPattern
                        .matcher(input)
                        .results()
                        .findAny()
                        .orElseThrow()
                        .group("seeds"))
                .results()
                .map(matchResult -> new SeedRange(
                        Long.parseLong(matchResult.group("start")),
                        Long.parseLong(matchResult.group("range"))))
                .toList();
    }
}
