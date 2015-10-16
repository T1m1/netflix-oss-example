package com.seitenbau.microservices.core.user.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {

	@Id
	private String userId;
	private String lastName;
	private String firstName;

	public User() {
	}

}
