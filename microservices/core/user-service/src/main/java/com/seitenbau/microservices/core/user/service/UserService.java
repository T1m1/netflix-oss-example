package com.seitenbau.microservices.core.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.user.UserRepository;
import com.seitenbau.microservices.core.user.model.User;

@RestController
@RequestMapping("/users")
public class UserService {

	@Autowired
	private UserRepository repository;

	@RequestMapping("/info")
	public String getStatus() {
		return "{\"timestamp\":\"" + new Date()
				+ "\",\"content\":\"I'm okay ;-)\"}";
	}

	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userId) {
		return repository.findOne(userId);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAll() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{userId}")
	public void delete(@PathVariable String userId) {
		repository.delete(userId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{userId}", consumes = "application/json")
	public User update(@PathVariable String userId, @RequestBody User user) {
		User update = repository.findOne(userId);
		update.setFirstName(user.getFirstName());
		update.setLastName(user.getLastName());
		return repository.save(update);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public User create(@RequestBody User user) {
		return repository.save(user);
	}

}
