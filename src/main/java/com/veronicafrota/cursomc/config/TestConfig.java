package com.veronicafrota.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.veronicafrota.cursomc.services.DBService;
import com.veronicafrota.cursomc.services.EmailService;
import com.veronicafrota.cursomc.services.MockEmailService;

// Profile specific test settings
// Just activate the @Beans when the test Profile is called 
@Configuration
@Profile("test") 									// Indicates that the setting is the test Profile 
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	// Just activate the @Beans when the test Profile is called
	@Bean
	public boolean instantiateDataBase() throws ParseException {			// Method responsible for instantiating the database Test Profile 
		dbService.instantiateTestDataBase();		// Calls the DBService to instantiate the database
		return true;
	} 



	// Search for the @Bean component
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
