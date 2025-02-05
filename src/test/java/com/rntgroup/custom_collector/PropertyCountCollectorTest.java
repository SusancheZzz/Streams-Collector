package com.rntgroup.custom_collector;

import com.rntgroup.custom_collector.entity.Property;
import com.rntgroup.custom_collector.entity.SomeEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PropertyCountCollectorTest {

    List<SomeEntity> entities;

    @Before
    public void init() {
        entities = List.of(
            new SomeEntity(Map.of(Property.PROP1, true, Property.PROP2, false, Property.PROP3, false)),
            new SomeEntity(Map.of(Property.PROP1, false, Property.PROP2, true, Property.PROP3, true)),
            new SomeEntity(Map.of(Property.PROP1, true, Property.PROP2, false, Property.PROP3, true))
        );
    }

    @Test
    public void testBooleanCountCollector() {
        Map<Property, Map<Boolean, Integer>> result = entities.stream()
            .collect(new PropertyCountCollector());

        result.forEach((property, counts) ->
            System.out.printf("For %s:%s%n", property, counts));

        assertEquals(Map.of(false, 1, true, 2), result.get(Property.PROP1));
        assertEquals(Map.of(false, 2, true, 1), result.get(Property.PROP2));
        assertEquals(Map.of(false, 1, true, 2), result.get(Property.PROP3));
    }
}

