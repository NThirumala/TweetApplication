package com.tweetapp.application.controller;

import java.security.Principal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.application.model.Tweet;
import com.tweetapp.application.service.TweetService;

@CrossOrigin(origins = "*")
@RestController
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/tweet/all")
	public ResponseEntity<List<Tweet>> getAllTweets(){
		return ResponseEntity.ok().body(tweetService.getAllTweets());
	}
	
	@GetMapping("/tweet/user")
	public ResponseEntity<List<Tweet>> getUserTweets(@RequestHeader(value="email", required= false) String email){
		return ResponseEntity.ok().body(tweetService.getUserTweets(email));
	}
	
	@PostMapping("/tweet/add")
	public ResponseEntity<?> postTweet(@RequestBody Tweet tweet){
		System.out.println("reached add tweet" +" "+ tweet);
//		tweet.setId(ObjectId.get());
		return ResponseEntity.ok().body(tweetService.save(tweet));
	} 
	
	@PostMapping("/tweet/update")
	public ResponseEntity<?> updateTweet(@RequestBody Tweet tweet, Principal principal){
		String loggedInUser = principal.getName();
		if(loggedInUser.equalsIgnoreCase(tweet.getEmail())){
			return ResponseEntity.ok().body(tweetService.updateTweet(tweet));
		}else {
			Map<String , String> error = new HashMap<>();
			error.put("StatusCode", "2");
			error.put("errorMsg" , "User not allowed to modify another user tweet message");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}
	}
	
	@DeleteMapping("/tweet/delete")
	public void deleteTweet(@RequestBody Tweet tweet){
		System.out.println(tweet);
		kafkaTemplate.send("deleteTweet", tweet.getId());
//		tweetService.deleteTweet(tweet);
	}
	
//	@DeleteMapping("/tweet/delete")
//	public void deleteTweet(@RequestBody Tweet tweet){
//		System.out.println(tweet);
//		tweetService.deleteTweet(tweet);
//	}
	
	@GetMapping("/tweet/like")
	public ResponseEntity <Tweet> likeTweet(@RequestHeader(value="id", required= false) String id ){
		System.out.println(id);
		return ResponseEntity.ok().body(tweetService.likeTweet(id));
	}
	
	@PostMapping("/tweet/reply")
	public ResponseEntity <Tweet> replyTweet(@RequestBody Tweet tweet){
		System.out.println(tweet);
		String parentTweetId =tweet.getId();
//		tweet.setId();
		Tweet tempTweet = tweet;
		System.out.println(tempTweet + " " + parentTweetId);
		return ResponseEntity.ok().body(tweetService.replyTweet(tempTweet, parentTweetId));
	}
	
//	@PostMapping("/tweet/reply")
//	public ResponseEntity <Tweet> replyTweet(@RequestBody ReTweet reTweet){
//		System.out.println(reTweet.toString());
//		Tweet tweet = reTweet.getRetweet();
//		String parentTweetId = reTweet.getParentTweetId();
//		System.out.println(tweet + " " + parentTweetId);
//		return ResponseEntity.ok().body(tweetService.replyTweet(tweet, parentTweetId));
//	}
	
}



class ReTweet {
	private Tweet retweet;
	private String parentTweetId;
	public Tweet getRetweet() {
		return retweet;
	}
	public void setRetweet(Tweet retweet) {
		this.retweet = retweet;
	}
	public String getParentTweetId() {
		return parentTweetId;
	}
	public void setParentTweetId(String parentTweetId) {
		this.parentTweetId = parentTweetId;
	}
	@Override
	public String toString() {
		return "ReTweet [retweet=" + retweet + ", parentTweetId=" + parentTweetId + "]";
	}
	public ReTweet(Tweet retweet, String parentTweetId) {
		super();
		this.retweet = retweet;
		this.parentTweetId = parentTweetId;
	}
	
}
