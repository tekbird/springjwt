package com.example;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class JwtAuthenticationException extends AuthenticationException {

	public JwtAuthenticationException(String msg) {
		super(msg);
	}

}
