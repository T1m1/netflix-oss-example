package com.seitenbau.microservices.core.user.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.user.model.User;

@RestController
public class UserService {
	
	@RequestMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {		
        return new User(userId, "Max", "Mustermann");
    }

}
