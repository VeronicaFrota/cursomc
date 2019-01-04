package com.veronicafrota.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.veronicafrota.cursomc.services.DBService;
import com.veronicafrota.cursomc.services.EmailService;
import com.veronicafrota.cursomc.services.SmtpEmailService;

// Profile specific test settings
// Just activate the @Beans when the test Profile is called 
@Configuration
@Profile("dev") 													// Indicates that the setting is the test Profile 
public class DevConfig {

	@Autowired
	private DBService dbService;

	// Just instantiate the data if the key (in aplication-dev.properties) are as create -> This is the key (spring.jpa.hibernate.ddl-auto)
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;			
	
	// Just activate the @bBeans when the test Profile is called
	@Bean
	public boolean instantiateDataBase() throws ParseException {	// Method responsible for instantiating the database Test Profile 

		if(!"create".equals(strategy)) {							// If the key is not to create, instantiate the data base, "Reseting" the database and creating always new 
			return false;
		}

		dbService.instantiateTestDataBase();						// Calls the DBService to instantiate the database

		return true;
	}



	// Search for the @Bean component
	// To send the real email
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
