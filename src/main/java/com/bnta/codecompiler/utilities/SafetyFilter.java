package com.bnta.codecompiler.utilities;

import com.bnta.codecompiler.models.dtos.CompileInput;

public class SafetyFilter {

    public static boolean isInputSafe(CompileInput input) {
        switch (input.getLang()) {
            case "java":
                return checkJava(input.getCode());
            case "js":
                return checkJs(input.getCode());
            case "py":
                return checkPy(input.getCode());
            default:
                return false;
        }
    }
    private static boolean checkJava(String src) {

        return isSafe(src, new String[]{
                "ProcessBuilder", "Runtime.getRuntime"
        });
    }

    private static boolean checkJs(String src) {
        return isSafe(src, new String[]{
                "require('child_process')", "require(\"child_process\")"
        });
    }

    private static boolean checkPy(String src) {
        return isSafe(src, new String[]{
                "", ""
        });
    }
    private static boolean isSafe(String src, String[] threats) {
        for(var threat : threats) {
            if(src.contains(threat)) return false;
        }

        return true;
    }
}
