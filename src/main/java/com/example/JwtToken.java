package com.example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@SuppressWarnings("serial")
public class JwtToken implements Authentication {

	final SignedJWT sjwt;
	private JWTClaimsSet claims;
	boolean authenticated;

	public JwtToken(SignedJWT sjwt) {
		this.sjwt = sjwt;
		this.authenticated = false;
		try {
			claims = this.sjwt.getJWTClaimsSet();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void clearClaims() {
		this.claims = new JWTClaimsSet.Builder().build();
	}

	public SignedJWT getSignedToken() {
		return this.sjwt;
	}

	@Override
	public String getName() {
		System.out.println("JwtToken.getName");
		return this.claims.getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String claimsString = (String) this.claims.getClaim("roles");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (claimsString != null && claimsString != "") {
			String[] roles = claimsString.split(",");
			System.out.println("JwtToken.getAuthorities.roles:" + roles);
			for (String role : roles) {
				System.out.println("JwtToken.getAuthorities.role:" + role);
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return Collections.unmodifiableList(grantedAuthorities);
	}

	@Override
	public Object getCredentials() {
		System.out.println("JwtToken.getCredentials");
		return "";
	}

	@Override
	public Object getDetails() {
		System.out.println("JwtToken.getDetails");
		return claims.toJSONObject();
	}

	@Override
	public Object getPrincipal() {
		System.out.println("JwtToken.getPrincipal");
		return claims.getSubject();
	}

	@Override
	public boolean isAuthenticated() {
		System.out.println("JwtToken.isAuthenticated");
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		System.out.println("JwtToken.setAuthenticated");
		this.authenticated = isAuthenticated;
	}

}
