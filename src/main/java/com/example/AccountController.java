package com.example;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.LoginModel;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@RestController
public class AccountController {

	private String SecretKey = "494847a9c8a147bf82f4ca6da59efe61";

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody LoginModel model, HttpServletRequest request) {
		try {
			JWSSigner signer = new MACSigner(SecretKey);
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.subject(model.username);
			builder.issuer("myself");
			builder.claim("roles", "ROLE_ADMIN");
			builder.expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
			try {
				signedJWT.sign(signer);
				return signedJWT.serialize();
			} catch (JOSEException e) {
				e.printStackTrace();
			}

		} catch (KeyLengthException e) {
			e.printStackTrace();
		}
		return "";
	}
}
