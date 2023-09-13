package com.bnta.codecompiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
@EnableAutoConfiguration
//@EnableCaching
public class BrightCodeApp {

	public static void main(String[] args) {
		SpringApplication.run(BrightCodeApp.class, args);
	}

}
