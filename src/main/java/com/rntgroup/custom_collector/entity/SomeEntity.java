package com.rntgroup.custom_collector.entity;

import lombok.Value;

import java.util.Map;


@Value
public class SomeEntity {

    Map<Property, Boolean> properties;
}
