package com.rntgroup.custom_collector;

import com.rntgroup.custom_collector.entity.Property;
import com.rntgroup.custom_collector.entity.SomeEntity;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PropertyCountCollector
    implements Collector<SomeEntity, Map<Property, Map<Boolean, Integer>>, Map<Property, Map<Boolean, Integer>>>
{

    @Override
    public Supplier<Map<Property, Map<Boolean, Integer>>> supplier() {
        return () -> new EnumMap<>(Property.class);
    }

    @Override
    public BiConsumer<Map<Property, Map<Boolean, Integer>>, SomeEntity> accumulator() {
        return (map, entity) -> entity
            .getProperties()
            .forEach(
                (property, value) -> map.computeIfAbsent(property, k -> new HashMap<>())
                .merge(value, 1, Integer::sum));
    }

    @Override
    public BinaryOperator<Map<Property, Map<Boolean, Integer>>> combiner() {
        return (map1, map2) -> {
            map2.forEach((property, counts) ->
                map1.merge(property, counts, (m1, m2) -> {
                    m2.forEach((key, value) -> m1.merge(key, value, Integer::sum));
                    return m1;
                }));
            return map1;
        };
    }

    @Override
    public Function<Map<Property, Map<Boolean, Integer>>, Map<Property, Map<Boolean, Integer>>> finisher() {
        return map -> map;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
