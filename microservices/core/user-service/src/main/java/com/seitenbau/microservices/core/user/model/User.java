package com.seitenbau.microservices.core.user.model;

import lombok.Data;

@Data
public class User {

	private String userId;
	private String lastname;
	private String firstname;

	public User(String userId, String firstname, String lastname) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
	}
}
