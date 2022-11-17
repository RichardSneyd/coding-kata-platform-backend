package com.bnta.codecompiler.config;

import com.bnta.codecompiler.components.converters.StringToDifficultyConverter;
import com.bnta.codecompiler.components.converters.StringToRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class SpringGlobalConfig implements WebMvcConfigurer {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://code.bntechacademy.com",
                "https://code.bntechacademy.com",
                "http://localhost:3000",
                "https://localhost:3000",
                "http://localhost",
                "https://localhost"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDifficultyConverter());
        registry.addConverter(new StringToRoleConverter());
    }


}