package com.bnta.codecompiler.utilities;
import com.bnta.codecompiler.models.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class JSONTest {
    private String simpleJsonString;

    @BeforeEach
    public void setUp(){
        simpleJsonString = "{\"name\":\"John Doe\", \"age\":35}";
    }

    @Test
    public void testParse() {
        JsonNode node = JSON.parse(simpleJsonString);
        assertEquals(node.get("name").asText(), "John Doe");
    }

    @Test
    public void testStringify() {
        Problem problem = new Problem("Add two numbers", Difficulty.VERY_EASY,
                new TestSuite(
                        new TestCase("{\"num1\":2,\"num2\":3}", "{\"result\":5}"),
                        new HashSet<TestCase>()
                ),
                new HashSet<String>(Arrays.asList(new String[]{"math"}))
        );
        JsonNode node = JSON.parse(JSON.stringify(problem));
        assertEquals(node.get("description").asText(), "My very easy problem");
        assertEquals(node.get("difficulty").asInt(), 0);
    }

    @Test
    public void testFromJson() {
       // JsonNode node = JSON.parse(new CodePojo("console.log('hello world');", "js"));
        JsonNode node = JSON.parse(simpleJsonString);
    }
}
