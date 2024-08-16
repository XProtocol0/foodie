package com.github.foodie.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class JsonConverter<T> implements AttributeConverter<T, String> {
    private final TypeReference<T> typeReference = new TypeReference<T>() {};
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(T object) {
        log.info("converting to database column");
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize JSON data", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String json) {
        log.info("converting to entity attribute");
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize JSON data", e);
        }
    }
}
