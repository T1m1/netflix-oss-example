package com.seitenbau.microservices.core.user.service;

import java.util.Date;
import java.util.UUID;

import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.toStringDocBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.user.UserRepository;
import com.seitenbau.microservices.core.user.model.User;

@RestController
public class UserService {
	
	@Autowired
	private UserRepository repository;

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId) {
		return repository.findOne(userId);
	}

	@RequestMapping("/info")
	public String getStatus() {
		return "{\"timestamp\":\"" + new Date()
				+ "\",\"content\":\"I'm okay ;-)\"}";
	}
	
	
	@RequestMapping(value = "/test/{testname}", method = RequestMethod.POST)
	public void testDB(@PathVariable String testname) {
		repository.save(new User(testname, testname));
		for (User user : repository.findAll()) {
			System.out.println(user.getFirstName() + user.getLastName());
		}
		
		System.out.println("Find user with name: " + testname);
		System.out.println(repository.findByFirstName(testname));
		
	}

}
