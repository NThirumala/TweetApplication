package com.tweetapp.application.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.application.model.User;

public interface UserRepo extends MongoRepository<User, Long>{
	User findByEmail(String email);
}
