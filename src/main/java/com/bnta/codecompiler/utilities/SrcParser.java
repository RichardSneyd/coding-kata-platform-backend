package com.bnta.codecompiler.utilities;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.DataType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SrcParser {
    public static  void echo(String val) {
        System.out.println(standardiseArgFormat(val));
    }
    public static String standardiseArgFormat(String str) {
        return str.replaceAll("['\" {}\\[\\]]", "");
    }

    public static String toArgument(Data data, String lang) {
        switch (data.getDataType()) {
            case INT, FLOAT, BOOLEAN:
                return data.getValue();
            case STRING:
                return wrapString(data.getValue());
            case INT_ARRAY, FLOAT_ARRAY, BOOLEAN_ARRAY, STRING_ARRAY:
                return wrapArray(data.getValue(), lang, getType(data.getDataType()));
                default:
                    return null;
        }
    }

    public static String toMethodCall(String methodName, List<Data> args,
                                      String lang) {
        var result = methodName + "(" + toArgs(args, lang) + ")";
        if(lang.equals("java")) result = "new Main()." + result;
      //  if(!lang.equals("py")) result = result + ";";
        return result;
    }

    public static String toArgs(List<Data> args, String lang) {
        String argsStr = "";
        for(int i = 0; i < args.size(); i++) {
            argsStr = argsStr + toArgument(args.get(i), lang);
            if(i != args.size()-1) argsStr = argsStr + ", ";
        }
        return argsStr;
    }

    public static String generateSrc(String functionName, String src,
                                     String lang, List<Data> inputs, DataType outputType) {
        src = src.replaceAll(functionName,   "solution");
        switch (lang) {
            case "java":
                return wrapJava(src, inputs, outputType);
            case "js":
                return wrapJs(src, inputs);
            case "py":
                return wrapPython(src, inputs);
            default:
                return src;
        }
    }

    public static String wrapJava(String src, List<Data> inputs, DataType type) {
        var insert = SrcParser.toMethodCall("solution", inputs, "java");
        switch (type) {
            case INT_ARRAY, FLOAT_ARRAY, STRING_ARRAY, BOOLEAN_ARRAY:
                src = "import java.util.Arrays; " + src;
                insert = "Arrays.toString(" + insert + ")";
        }
        insert =
                "public static void main(String[] args){" +
                        "System.out.println(" + insert +
                        ");}";
        var lastIndex = src.lastIndexOf("}") > -1 ? src.lastIndexOf("}") : 1;
        return src.substring(0, lastIndex) + insert + "}";
    }

    public static String wrapJs(String src, List<Data> inputs) {
        var insert = SrcParser.toMethodCall("solution", inputs, "js");
        return src + "\nconsole.log(" + insert + ");";

    }

    public static String wrapPython(String src, List<Data> inputs) {
        var insert = SrcParser.toMethodCall("solution", inputs, "py");
        return src + "\nprint(" + insert + ")";
    }

    public static String getType(DataType dt) {
        switch(dt) {
            case FLOAT_ARRAY, FLOAT:
                return "float";
            case STRING_ARRAY, STRING:
                return "String";
            case INT_ARRAY, INT:
                return "int";
            case BOOLEAN_ARRAY, BOOLEAN:
                return "boolean";
        }
        return null;
    }

    public static String wrapArray(String value, String lang, String type) {
        if(type.equals("String")) value = addQuotesToArr(value);
        return lang.equals("js") || lang.equals("py") ? wrapJsArray(value) :
                javaTypedArray(value, type);
    }


    public static String wrapJsArray(String array) {
        return "[" + array + "]";
    }

    public static String javaTypedArray(String array, String type) {
        return "new " + type + "[]" + wrapJavaArray(array, type);
    }
    public static String wrapJavaArray(String array, String type) {
        return "{" + array + "}";
    }


    public static String addQuotesToArr(String val) {
        return Arrays.stream(val.split(",")).map(SrcParser::wrapString).collect(Collectors.joining(","));
    }

    public static String wrapString(String val) { return  "\"" + val + "\"";}

    public static String removeLogs(String src, String lang) {
        String pattern = lang == "java" ? "System.out.println(.*?);"
                : lang == "js" ? "console.log(.*?)(\\)|\\);)"
                : "print([ \\(])(.*?)\\)";
        return src.replaceAll(pattern, "");
    }

}
