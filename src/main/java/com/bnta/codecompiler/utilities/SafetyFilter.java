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
                "java.lang.ProcessBuilder", "Runtime.getRuntime",
                "java.lang.Runtime", "java.io.File", "java.util.Scanner"
        });
    }

    private static boolean checkJs(String src) {
        return isSafe(src, new String[]{
                "require('child_process')", "require(\"child_process\")", "spawn", "execFile", "execSync"
        });
    }

    private static boolean checkPy(String src) {
        return isSafe(src, new String[]{
                "os.system", "subprocess.Popen", "eval", "exec"
        });
    }
    private static boolean isSafe(String src, String[] threats) {
        for(var threat : threats) {
            if(src.contains(threat)) return false;
        }

        return true;
    }
}
