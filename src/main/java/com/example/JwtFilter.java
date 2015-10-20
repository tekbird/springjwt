package com.example;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import com.nimbusds.jwt.SignedJWT;

public class JwtFilter extends GenericFilterBean {

	AuthenticationManager authenticationManager;

	AuthenticationEntryPoint entryPoint;

	public JwtFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint entryPoint) {
		this.authenticationManager = authenticationManager;
		this.entryPoint = entryPoint;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("JwtFilter.doFilter");
		System.out.println("JwtFilter.authenticationManager" + authenticationManager);
		System.out.println("JwtFilter.entryPoint" + entryPoint);

		HttpServletRequest req = (HttpServletRequest) request;

		String stringToken = req.getHeader("Authorization");
		System.out.println("JwtFilter.doFilter.stringToken:" + stringToken);

		if (stringToken != null && stringToken != "") {
			try {
				SignedJWT sjwt = SignedJWT.parse(stringToken);
				JwtToken token = new JwtToken(sjwt);
				Authentication auth = authenticationManager.authenticate((Authentication) token);
				if (!auth.isAuthenticated()) {
					throw new JwtAuthenticationException("failed");
				}
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (AuthenticationException e) {
				e.printStackTrace();
			}
		}

		chain.doFilter(request, response);
	}

	// @Override
	// public void doFilter(ServletRequest request, ServletResponse response,
	// FilterChain chain)
	// throws IOException, ServletException {
	//

	// }

}
