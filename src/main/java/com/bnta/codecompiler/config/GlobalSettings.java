package com.bnta.codecompiler.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GlobalSettings {
//    public final static boolean inProduction = false;
    private static final String CONFIG_FILE = ".env";
    private static Properties properties = new Properties();
    private static String DOMAIN_NAME = "http://bntechacademy.com";
    private static String API_PORT = "8080";
    private static String FRONTEND_PORT = "80";

    static {
        try {
            Path configFilePath = Paths.get(CONFIG_FILE);
            if (Files.exists(configFilePath)) {
                try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
                    properties.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("The specified config file does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDomainName() {
        return inProduction() ? DOMAIN_NAME : properties.getProperty("DOMAIN_NAME");
    }

    public static String getAPIPort() {
        return inProduction() ? API_PORT : properties.getProperty("API_PORT");
    }

    public static String getFrontendPort() {
        return inProduction() ? FRONTEND_PORT : properties.getProperty("FRONTEND_PORT");
    }


    public static boolean inProduction() {
        return properties.getProperty("Environment") == null || !properties.getProperty("ENVIRONMENT").equals("development");
    }


    public static String getApiOrigin() {
        return getDomainName() + ":" + getAPIPort();
    }

    public static String getFrontEndOrigin() {
        return getDomainName() + ":" + getFrontendPort();
    }



}
