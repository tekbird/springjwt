package com.example;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessDeniedExceptionHandler {
	@ExceptionHandler(value = AccessDeniedException.class)
	public void handle(HttpServletResponse reponse) {
		System.out.println("AccessDeniedExceptionHandler.handle");

		reponse.addHeader("X-Unauthorized", "1");
		reponse.setStatus(200);
	}
}