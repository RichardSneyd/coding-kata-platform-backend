package com.bnta.codecompiler.config;


public class GlobalSettings {
    public final static boolean inProduction = false;
    private static String DOMAIN_NAME = "http://bntechacademy.com";
    private static String API_PORT = "8080";
    private static String FRONTEND_PORT = "80";

    public static String getDomainName() {
        return inProduction ? DOMAIN_NAME : "http://localhost";
    }

    public static String getApiOrigin() {
        return getDomainName() + ":" + API_PORT;
    }

    public static String getFrontEndOrigin() {
        return getDomainName() + ":" + FRONTEND_PORT;
    }

    public static String getApiPort() {
        return API_PORT;
    }

    public static String getFrontendPort() {
        return FRONTEND_PORT;
    }


}
