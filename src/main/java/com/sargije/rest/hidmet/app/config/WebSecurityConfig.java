package com.sargije.rest.hidmet.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	PersistentTokenRepository persistentTokenRepository;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
		//.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	/*	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
		.antMatchers("/rest/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
		.and().formLogin();
		
	}*/
	/*@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .csrf().disable()
	      .authorizeRequests()
	        .antMatchers(HttpMethod.POST, "/api/**").authenticated()
	        .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
	        .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
	        .antMatchers(HttpMethod.GET, "/rest/**").access("hasRole('ROLE_ADMIN')")//.authenticated()
	        .anyRequest().permitAll()
	        .and()
		    .httpBasic().and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  }*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.authorizeRequests()
	    .anyRequest().access("hasAuthority('admin')")
	    //.and().formLogin()
		//.loginPage("/login").failureUrl("/login?error")
		//.usernameParameter("username")
		//.passwordParameter("password")
		//.and().logout().logoutSuccessUrl("/login?logout")
	    //.anyRequest().permitAll()
		.and().csrf()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
