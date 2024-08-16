package com.github.foodie.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Converter
@Slf4j
public class UUIDListConvertor extends JsonConverter<List<UUID>> {
    private final TypeReference<List<UUID>> typeReference = new TypeReference<List<UUID>>() {};
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<UUID> uuidList) {
        log.info("converting UUID list to database column");
        try {
            return objectMapper.writeValueAsString(uuidList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize JSON data", e);
        }
    }

    @Override
    public List<UUID> convertToEntityAttribute(String json) {
        log.info("converting JSON to UUID list");
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize JSON data", e);
        }
    }

}
