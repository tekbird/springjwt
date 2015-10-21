package com.models;

import org.hibernate.validator.constraints.NotBlank;

public class LoginModel {
	@NotBlank(message = "username is required")
	public String username;
	@NotBlank(message = "password is required")
	public String password;
}
