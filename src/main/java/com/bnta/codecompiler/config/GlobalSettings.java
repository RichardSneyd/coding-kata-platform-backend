package com.bnta.codecompiler.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class GlobalSettings {
    private Properties properties = new Properties();

    @Value("${DOMAIN_NAME}")
    private String DOMAIN_NAME;

    @Value("${API_PORT}")
    private String API_PORT;

    @Value("${FRONTEND_PORT}")
    private String FRONTEND_PORT;

    public String getDomainName() {
        return inProduction() ? DOMAIN_NAME : properties.getProperty("DOMAIN_NAME");
    }

    public String getAPIPort() {
        return inProduction() ? API_PORT : properties.getProperty("API_PORT");
    }

    public String getFrontendPort() {
        return inProduction() ? FRONTEND_PORT : properties.getProperty("FRONTEND_PORT");
    }

    public boolean inProduction() {
        return properties.getProperty("Environment") == null || !properties.getProperty("ENVIRONMENT").equals("development");
    }


    public String getApiOrigin() {
        return getDomainName() + ":" + getAPIPort();
    }

    public String getFrontEndOrigin() {
        return getDomainName() + ":" + getFrontendPort();
    }

}
