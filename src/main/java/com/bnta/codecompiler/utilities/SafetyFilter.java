package com.bnta.codecompiler.utilities;

public class SafetyFilter {
    public boolean checkJava(String src) {

        return isSafe(src, new String[]{
                "ProcessBuilder", "Runtime.getRuntime"
        });
    }

    public boolean checkJs(String src) {
        return isSafe(src, new String[]{
                "require('child_process')", "require(\"child_process\")"
        });
    }

    public boolean checkPy(String src) {
        return isSafe(src, new String[]{
                "", ""
        });
    }
    private boolean isSafe(String src, String[] smells) {
        for(var smell : smells) {
            if(src.contains(smell)) return false;
        }

        return true;
    }
}
