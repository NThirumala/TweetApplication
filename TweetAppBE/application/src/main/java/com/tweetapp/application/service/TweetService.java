package com.tweetapp.application.service;

import java.util.List;

import com.tweetapp.application.model.Tweet;

public interface TweetService {
	List<Tweet> getAllTweets();
	List<Tweet> getUserTweets(String email);
	Tweet save(Tweet tweet);
	Tweet updateTweet(Tweet tweet);
	void deleteTweet(Tweet tweet);
	Tweet likeTweet(String id);
	Tweet replyTweet(Tweet tweet, String tweetId);
}
