package com.example;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "test succeeded";
	}

	@RequestMapping("/securetest")
	@Secured({ "ROLE_ADMIN" })
	public String securetest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("securetest:auth=" + auth);
		return "securetest succeeded";
	}
}
