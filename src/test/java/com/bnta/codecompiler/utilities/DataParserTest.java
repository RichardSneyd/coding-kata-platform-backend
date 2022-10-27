package com.bnta.codecompiler.utilities;


import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DataParserTest {
    private String sampleStringArr;
    private String sampleBooleanArr;
    private String sampleIntArr;

    @BeforeEach
    public void setUp() {
        sampleStringArr = "Dog,Cat,Horse";
        sampleBooleanArr = "true,false,true";
        sampleIntArr = "1,2,3,4";
    }

    @Test
    public void testStandarise() {
        assertThat(DataParser.standardise(Arrays.toString(new int[]{0, 1, 4, 5}))).isEqualTo("0,1,4,5");
        assertThat(DataParser.standardise(Arrays.toString(new String[]{"Dog", "Cat", "Horse"}))).isEqualTo("Dog,Cat,Horse");
    }

    @Test
    public void testToArgs() {
        assertThat(DataParser.toArgs(List.of(
                new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                        new Data("50", DataType.INT)
                ),
                "java"))
                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Fish\"}, 50");
    }

    @Test
    public void testToArgument() {
        assertThat(DataParser.toArgument(
                new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                "java"))
                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Fish\"}");
    }

    @Test
    public void testToMethodCall() {
        assertThat(DataParser.toMethodCall("sum", List.of(
                        new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                        new Data("50", DataType.INT)),
                "java"))
                .isEqualTo("new Main().sum(new String[]{\"Dog\",\"Cat\",\"Fish\"}, 50)");
    }


    @Test
    public void testGenerateSrc() {
        assertThat(DataParser.generateSrc("sum", "public class Main {" +
                "public int sum(int[] vals){" +
                "int result = 0;" +
                "for(var val : vals) {result += val;}" +
                "return result;" +
                "}\n" +
                "}\n", "java", List.of(new Data("5,2,4",
                DataType.INT_ARRAY)), DataType.INT_ARRAY))
                .isEqualTo("import java.util.Arrays; " +
                        "public class Main {" +
                        "public int solution(int[] vals){" +
                        "int result = 0;" +
                        "for(var val : vals) {result += val;}" +
                        "return result;" +
                        "}\npublic static void main(String[] args){" +
                        "System.out.println(Arrays.toString(new Main().solution(new int[]{5,2,4})));" +
                        "}}");
    }

    @Test
    public void testWrapJava() {
        String src = "public class Main {" +
                "public String solution(a, b) {" +
                   "return a + b;" +
                "} " +
                "}";
        assertThat(DataParser.wrapJava(
                src, List.of(
                        new Data("5", DataType.INT),
                        new Data("5", DataType.INT)),
                        DataType.INT
                )).isEqualTo("public class Main {" +
   "public String solution(a, b) {" +
            "return a + b;" +
        "} " +
        "public static void main(String[] args){" +
            "System.out.println(new Main().solution(5, 5));" +
        "}" +
    "}");
    }

    @Test
    public void testWrapJs() {
        String src = "function solution(a, b){\n" +
                "return a + b;\n}";
        assertThat(DataParser.wrapJs(
                src, List.of(
                        new Data("5", DataType.INT),
                        new Data("5", DataType.INT)
                ))).isEqualTo("function solution(a, b){\n" +
                "return a + b;\n}\nconsole.log(solution(5, 5));");
    }

    @Test
    public void testWrapPython() {
        String src = "fun solution(a, b):\n" +
                "       return a + b\n";
        assertThat(DataParser.wrapPython(
                src, List.of(
                        new Data("5", DataType.INT),
                        new Data("5", DataType.INT)
                ))).isEqualTo("fun solution(a, b):\n" +
                "       return a + b\n\nprint(solution(5, 5))");
    }

    @Test
    public void testGetType() {
        assertThat(DataParser.getType(DataType.BOOLEAN_ARRAY))
                .isEqualTo("boolean");
        assertThat(DataParser.getType(DataType.INT_ARRAY))
                .isEqualTo("int");
        assertThat(DataParser.getType(DataType.FLOAT_ARRAY))
                .isEqualTo("float");
        assertThat(DataParser.getType(DataType.STRING_ARRAY))
                .isEqualTo("String");
    }

    @Test
    public void testWrapArray() {
        assertThat(DataParser.wrapArray(sampleStringArr, "js", "String"))
                .isEqualTo("[\"Dog\",\"Cat\",\"Horse\"]");
        assertThat(DataParser.wrapArray(sampleStringArr, "java", "String"))
                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Horse\"}");
        assertThat(DataParser.wrapArray(sampleIntArr, "java", "int"))
                .isEqualTo("new int[]{1,2,3,4}");
        assertThat(DataParser.wrapArray(sampleIntArr, "js", "int"))
                .isEqualTo("[1,2,3,4]");
        assertThat(DataParser.wrapArray(sampleBooleanArr, "java", "boolean"))
                .isEqualTo("new boolean[]{true,false,true}");
        assertThat(DataParser.wrapArray(sampleBooleanArr, "js", "boolean"))
                .isEqualTo("[true,false,true]");
    }

    @Test
    public void testWrapJsArray() {
        assertThat(DataParser.wrapJsArray(sampleStringArr))
                .isEqualTo("[Dog,Cat,Horse]");
    }

    @Test
    public void javaTypedArray() {
        assertThat(DataParser.javaTypedArray(sampleStringArr, "String"))
                .isEqualTo("new String[]{Dog,Cat,Horse}");
        assertThat(DataParser.javaTypedArray("1,2,3,4", "int"))
                .isEqualTo("new int[]{1,2,3,4}");
    }

    @Test
    public void testWrapJavaArray() {
        assertThat(DataParser.wrapJavaArray(sampleStringArr, "String"))
                .isEqualTo("{Dog,Cat,Horse}");
        assertThat(DataParser.wrapJavaArray("1,2,3,4", "int"))
                .isEqualTo("{1,2,3,4}");
    }

    @Test
    public void testAddQuotesToArr() {
        assertThat(DataParser.addQuotesToArr(sampleStringArr))
                .isEqualTo("\"Dog\",\"Cat\",\"Horse\"");
    }

    @Test
    public void testWrapString() {
        assertThat(DataParser.wrapString("Hello World"))
                .isEqualTo("\"Hello World\"");
    }

}
