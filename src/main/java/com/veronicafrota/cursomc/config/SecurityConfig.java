package com.veronicafrota.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.veronicafrota.cursomc.security.JWTAuthenticationFilter;
import com.veronicafrota.cursomc.security.JWTAuthorizationFilter;
import com.veronicafrota.cursomc.security.JWTUtil;

// To speak what will be released, or not, by default
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)				// To authorize only certain specific profiles
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	// Vector to tell which paths by default will be freed for access (When logged in)
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};


	// Permissions that will allow only GET
	// Vector with the read-only paths, so that anyone who isn't logged in can view catalogs of categories
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/estados/**"
	};


	// Permissions that will allow only POST (Accesses without being logged in)
	// Vector with the read-only paths, so that anyone who isn't logged in can view catalogs of categories
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes/**",
			"/auth/forgot/**"
	};



	// Setting to tell which paths are allowed
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Get the active Profiles, if you are on the test, use the H2 database
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();										// To release access to the H2
		}
		
		http.cors().and().csrf().disable();													// Call method cors And it disables the attack of csrf (caching-related attack)
		http.authorizeRequests()
		 	.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()					// Only allows the method to get access to this way
		 	.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()					// Only allows the method to get access to this way
		 	.antMatchers(PUBLIC_MATCHERS).permitAll()										// All inside the vector are ok
			.anyRequest().authenticated();													// For everything, requires authentication

		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));		// Filter to verify user authentication (generate token)
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));		// Filter to verify user authorization (generate token)
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 	// Ensures that the banckend does not create user session 
	}



	 // Method to know who is the UserDetaisService used and what is the encryption algorithm (bCrypt)
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	


	// Allow endpoint access of multiple paths with basic configurations
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }



	// To encrypt the password
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
