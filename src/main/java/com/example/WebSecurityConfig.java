package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("WebSecurityConfig.configure@AuthenticationManagerBuilder");

		auth.authenticationProvider(new JwtAuthenticationProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("WebSecurityConfig.configure@HttpSecurity");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterAfter(new JwtFilter(authenticationManagerBean(), new JwtAuthenticationEntryPoint()),
				AnonymousAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				System.out.println("JwtAuthenticationException.commence");
				response.addHeader("X-Unauthorized", "1");
				response.setStatus(200);

			}
		});

	}

}