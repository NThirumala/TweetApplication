package com.tweetapp.application.controller;

import java.security.Principal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.application.exception.CustomException;
import com.tweetapp.application.model.User;
import com.tweetapp.application.service.UserService;
import com.tweetapp.application.service.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
	}
	@GetMapping("/user")
	public ResponseEntity<User> getUser(@RequestHeader(value="email") String email){
		System.out.println(email);
		return ResponseEntity.ok().body(userService.getUser(email));
	}
//	@GetMapping("/user")
//	public ResponseEntity<User> getUser(@RequestBody User user){
//		System.out.println(user.getEmail());
//		return ResponseEntity.ok().body(userService.getUser(user.getEmail()));
//	}
	@PostMapping("/user/save")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws CustomException {
		System.out.println("registering new user");
		if(user.getFirstName() == null || user.getEmail() == null || user.getPassword() == null || user.getGender() == null) {
			Map<String , String> error = new HashMap<>();
			error.put("StatusCode", "3");
			error.put("errorMsg" , "Required user deatils not available. New user registration failed");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}else {
			try {
				return ResponseEntity.ok().body(userService.saveUser(user));
			}catch(CustomException e) {
				Map<String, String> response = new HashMap<>();
				response.put("StatusCode", "11000");
				response.put("Description" , e.getMessage());
				System.out.println(response);
				return ResponseEntity.ok().body(response);
			}
		}
	}
	
	@PostMapping("/user/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody User user, Principal principal ){
		String loggedInUser = principal.getName();
		if(loggedInUser.equalsIgnoreCase(user.getEmail())) {
			return ResponseEntity.ok().body(userService.updatePassword(user));
		}else {
			logger.info("User not allowed to update password of another user");
			Map<String , String> error = new HashMap<>();
			error.put("StatusCode", "1");
			error.put("errorMsg" , "User not allowed to update password of another user");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}
	}
}
