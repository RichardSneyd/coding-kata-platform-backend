package com.bnta.codecompiler;

import com.bnta.codecompiler.services.email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
@EnableAutoConfiguration
public class CodeCompilerApplication {
	@Autowired
	private MailSenderService mailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(CodeCompilerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
	//	mailSenderService.sendEmail("richardsneyd@hotmail.com", "test email", "It worked!");
	}


}
