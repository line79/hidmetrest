package com.sargije.rest.hidmet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class HidmetRestApplication extends SpringBootServletInitializer  {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(HidmetRestApplication.class);
	}
	 
	public static void main(String[] args) {
		SpringApplication.run(HidmetRestApplication.class, args);
	}
	
}