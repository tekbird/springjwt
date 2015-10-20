package com.example;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private String SecretKey = "494847a9c8a147bf82f4ca6da59efe61";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("JwtAuthenticationProvider.authenticate");
		JwtToken jwtToken = (JwtToken) authentication;
		System.out.println("JwtAuthenticationProvider.authenticate:jwtToken=" + jwtToken);
		try {
			JWSVerifier verifier = new MACVerifier(SecretKey);
			boolean isVerified = jwtToken.getSignedToken().verify(verifier);
			if (isVerified) {
				jwtToken.setAuthenticated(true);
			} else {
				jwtToken.setAuthenticated(false);
				jwtToken.clearClaims();
			}
			return jwtToken;
		} catch (JOSEException e) {
			throw new JwtAuthenticationException("authentication failed");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
