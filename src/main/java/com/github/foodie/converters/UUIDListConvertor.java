package com.github.foodie.converters;

import jakarta.persistence.Converter;

import java.util.List;
import java.util.UUID;

@Converter
public class UUIDListConvertor extends JsonConverter<List<UUID>> {

}
