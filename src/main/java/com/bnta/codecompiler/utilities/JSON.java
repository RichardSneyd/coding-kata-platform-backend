package com.bnta.codecompiler.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {

    private static ObjectMapper objectMapper = null;

    private static ObjectMapper getDefaultObjectMapper() {
        if(objectMapper == null) objectMapper = new ObjectMapper();
                // configure...
        return objectMapper;
    }

    public static JsonNode parse(String src) {
        try {
            return getDefaultObjectMapper().readTree(src);
        } catch (JsonProcessingException e) {
         //   throw new RuntimeException(e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(JsonNode node, Class<T> clazz) {
        try {
            return getDefaultObjectMapper().treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stringify(Object node) {
        try {
            return getDefaultObjectMapper().writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
