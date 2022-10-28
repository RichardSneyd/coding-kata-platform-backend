package com.bnta.codecompiler.utilities;
import com.bnta.codecompiler.models.problems.Difficulty;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.problems.StartCode;
import com.bnta.codecompiler.models.tests.TestCase;
import com.bnta.codecompiler.models.tests.TestSuite;
import com.bnta.codecompiler.models.users.User;
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
        Problem problem = new Problem("Add two numbers", "Add two numbers and return.", Difficulty.VERY_EASY,
                new TestSuite(
                        new HashSet<TestCase>(),
                        new HashSet<TestCase>()
                ),
                new StartCode(),
                new HashSet<String>(Arrays.asList("math"))
        );
        JsonNode node = JSON.parse(JSON.stringify(problem));
        assertEquals(node.get("description").asText(), "Add two numbers and return.");
        assertEquals(node.get("difficulty").asInt(), 0);
    }

    @Test
    public void testJsoneNodeToCLass() {
       // JsonNode node = JSON.parse(new CodePojo("console.log('hello world');", "js"));
       var json = JSON.stringify(new User());
       var node = JSON.parse(json);
       var user = JSON.jsonNodeToClass(node, User.class);
    }
}
