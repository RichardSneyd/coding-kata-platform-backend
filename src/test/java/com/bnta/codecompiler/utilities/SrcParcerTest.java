package com.bnta.codecompiler.utilities;

import com.bnta.codecompiler.models.dtos.CompileInput;
import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SrcParcerTest {
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
                assertThat(SrcParser.standardiseArgFormat(Arrays.toString(new int[] { 0, 1, 4, 5 })))
                                .isEqualTo("0,1,4,5");
                assertThat(SrcParser.standardiseArgFormat(Arrays.toString(new String[] { "Dog", "Cat", "Horse" })))
                                .isEqualTo("Dog,Cat,Horse");
        }

        @Test
        public void testToArgs() {
                assertThat(SrcParser.toArgs(List.of(
                                new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                                new Data("50", DataType.INT)),
                                "java"))
                                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Fish\"}, 50");
        }

        @Test
        public void testToArgument() {
                assertThat(SrcParser.toArgument(
                                new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                                "java"))
                                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Fish\"}");
        }

        @Test
        public void testToMethodCall() {
                assertThat(SrcParser.toMethodCall("sum", List.of(
                                new Data("Dog,Cat,Fish", DataType.STRING_ARRAY),
                                new Data("50", DataType.INT)),
                                "java"))
                                .isEqualTo("new Main().sum(new String[]{\"Dog\",\"Cat\",\"Fish\"}, 50)");
        }

        @Test
        public void testGenerateSrc() {
                assertThat(SrcParser.generateSrc("sum", "public class Main {" +
                                "public int sum(int[] vals){" +
                                "int result = 0;" +
                                "for(var val : vals) {result += val;}" +
                                "return result;" +
                                "}\n" +
                                "}\n", "java",
                                List.of(new Data("5,2,4",
                                                DataType.INT_ARRAY)),
                                DataType.INT_ARRAY))
                                .isEqualTo("import java.util.Arrays; " +
                                                "public class Main {" +
                                                "public int sum(int[] vals){" +
                                                "int result = 0;" +
                                                "for(var val : vals) {result += val;}" +
                                                "return result;" +
                                                "}\npublic static void main(String[] args){" +
                                                "System.out.println(Arrays.toString(new Main().sum(new int[]{5,2,4})));"
                                                +
                                                "}}");
        }

        @Test
        public void testWrapJava() {
                String src = "public class Main {" +
                                "public String solution(a, b) {" +
                                "return a + b;" +
                                "} " +
                                "}";
                assertThat(SrcParser.wrapJava(
                                src, List.of(
                                                new Data("5", DataType.INT),
                                                new Data("5", DataType.INT)),
                                DataType.INT, "solution")).isEqualTo("public class Main {" +
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
                assertThat(SrcParser.wrapJs(
                                src, List.of(
                                                new Data("5", DataType.INT),
                                                new Data("5", DataType.INT)),
                                "solution")).isEqualTo("function solution(a, b){\n" +
                                                "return a + b;\n}\nconsole.log(solution(5, 5));");
        }

        @Test
        public void testWrapPython() {
                String src = "fun solution(a, b):\n" +
                                "       return a + b\n";
                assertThat(SrcParser.wrapPython(
                                src, List.of(
                                                new Data("5", DataType.INT),
                                                new Data("5", DataType.INT)),
                                "solution")).isEqualTo("fun solution(a, b):\n" +
                                                "       return a + b\n\nprint(solution(5, 5))");
        }

        @Test
        public void testGetType() {
                assertThat(SrcParser.getType(DataType.BOOLEAN_ARRAY))
                                .isEqualTo("boolean");
                assertThat(SrcParser.getType(DataType.INT_ARRAY))
                                .isEqualTo("int");
                assertThat(SrcParser.getType(DataType.FLOAT_ARRAY))
                                .isEqualTo("float");
                assertThat(SrcParser.getType(DataType.STRING_ARRAY))
                                .isEqualTo("String");
        }

        @Test
        public void testWrapArray() {
                assertThat(SrcParser.wrapArray(sampleStringArr, "js", "String"))
                                .isEqualTo("[\"Dog\",\"Cat\",\"Horse\"]");
                assertThat(SrcParser.wrapArray(sampleStringArr, "java", "String"))
                                .isEqualTo("new String[]{\"Dog\",\"Cat\",\"Horse\"}");
                assertThat(SrcParser.wrapArray(sampleIntArr, "java", "int"))
                                .isEqualTo("new int[]{1,2,3,4}");
                assertThat(SrcParser.wrapArray(sampleIntArr, "js", "int"))
                                .isEqualTo("[1,2,3,4]");
                assertThat(SrcParser.wrapArray(sampleBooleanArr, "java", "boolean"))
                                .isEqualTo("new boolean[]{true,false,true}");
                assertThat(SrcParser.wrapArray(sampleBooleanArr, "js", "boolean"))
                                .isEqualTo("[true,false,true]");
        }

        @Test
        public void testWrapJsArray() {
                assertThat(SrcParser.wrapJsArray(sampleStringArr))
                                .isEqualTo("[Dog,Cat,Horse]");
        }

        @Test
        public void javaTypedArray() {
                assertThat(SrcParser.javaTypedArray(sampleStringArr, "String"))
                                .isEqualTo("new String[]{Dog,Cat,Horse}");
                assertThat(SrcParser.javaTypedArray("1,2,3,4", "int"))
                                .isEqualTo("new int[]{1,2,3,4}");
        }

        @Test
        public void testWrapJavaArray() {
                assertThat(SrcParser.wrapJavaArray(sampleStringArr, "String"))
                                .isEqualTo("{Dog,Cat,Horse}");
                assertThat(SrcParser.wrapJavaArray("1,2,3,4", "int"))
                                .isEqualTo("{1,2,3,4}");
        }

        @Test
        public void testAddQuotesToArr() {
                assertThat(SrcParser.addQuotesToArr(sampleStringArr))
                                .isEqualTo("\"Dog\",\"Cat\",\"Horse\"");
        }

        @Test
        public void testWrapString() {
                assertThat(SrcParser.wrapString("Hello World"))
                                .isEqualTo("\"Hello World\"");
        }

    @Test
    public void testRemoveLogsJava() {
        var input = new CompileInput("public class App { \nSystem.out.println(\"Logs not removed...\");}", "java");
        assertThat(SrcParser.removeLogs(input.getCode(), input.getLang()))
                .isEqualTo("public class App { \n }");

//    @Test
//    public void testRemoveLogsJs() {
//        var src = "function Horse(name) {console.log('hello world')}console.log(\"hello badger\")";
//        assertThat(SrcParser.removeLogs(src, "js")).isEqualTo("function Horse(name) {}");
//    }

        @Test
        public void testRemoveLogsJs() {
                var src = "function Horse(name) { console.log('hello world') } console.log(\"hello badger\")";
                assertThat(SrcParser.removeLogs(src, "js")).isEqualTo("function Horse(name) {   }  ");
        }

        @Test
        public void testRemoveLogsPy() {
                var src = "def my_function(fname):\n\sprint(fname)\n\sprint(\"Horse\")";
                assertThat(SrcParser.removeLogs(src, "py").replaceAll(" ", ""))
                                .isEqualTo("def my_function(fname):\n\s\n\s".replaceAll(" ", ""));
        }
}
