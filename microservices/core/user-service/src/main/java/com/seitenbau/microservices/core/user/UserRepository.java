package com.seitenbau.microservices.core.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.seitenbau.microservices.core.user.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByFirstName(String firstName);

}
