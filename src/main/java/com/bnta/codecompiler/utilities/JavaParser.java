package com.bnta.codecompiler.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaParser {


    public static List<String> removeLinesWith(List<String> lines, List<String> subs) {
        lines = Stream.of(lines.toArray(new String[0])).filter(line -> {
            for(var sub : subs) {
                if(line.indexOf(sub) == 1) return false;
            }

            return true;
        }).collect(Collectors.toList());
        return lines;
    }
    public static String join(List<String> lines) {
        return String.join(";\n");
    }

    public static List<String> getLines(String src) {
        return Arrays.asList(src.split(";\n"));
    }
}
