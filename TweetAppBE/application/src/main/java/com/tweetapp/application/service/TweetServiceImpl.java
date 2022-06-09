package com.tweetapp.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.application.model.Tweet;
import com.tweetapp.application.repo.TweetRepo;

@Service @Transactional
public class TweetServiceImpl implements TweetService{
	
	@Autowired
	private TweetRepo tweetRepo;

	
	@Override
	public List<Tweet> getAllTweets() {
		System.out.println(tweetRepo.findAll());
		return tweetRepo.findAll();
	}

	@Override
	public List<Tweet> getUserTweets(String email) {
		System.out.println("Service in=mpl");
		System.out.println(email);
		System.out.println(tweetRepo.findByEmail(email));
		return tweetRepo.findByEmail(email);
	}

	@Override
	public Tweet save(Tweet tweet) {
		System.out.println(tweet);
		tweetRepo.save(tweet);
		return tweet;
	}

	@Override
	public Tweet updateTweet(Tweet tweet) {
		Tweet temp = tweetRepo.findById(String.valueOf(tweet.getId()));
		temp.setTweetMsg(tweet.getTweetMsg());
		temp.setTime(tweet.getTime());
		tweetRepo.save(temp);
		return temp;
	}
	
	@KafkaListener(topics = "deleteTweet", groupId = "group-id")
	   public void listen(String id) {
	      System.out.println("Received ID in group - group-id: " + id);
			tweetRepo.deleteById(id);
	   }

	@Override
	public void deleteTweet(Tweet tweet) {
//		tweetRepo.deleteById(tweet.getId());
	}

	@Override
	public Tweet likeTweet(String id) {
		Tweet temp = tweetRepo.findById(id);
		int likes = temp.getLike();
		temp.setLike(likes +1);
		tweetRepo.save(temp);
		return temp;
	}

	@Override
	public Tweet replyTweet(Tweet retweet, String tweetId) {
		String TweetId = tweetId;
		Tweet temp = tweetRepo.findById(TweetId);
		List<Tweet> replyTweets = temp.getReplyTweet();
		retweet.setId(String.valueOf(replyTweets.size()+1));
		replyTweets.add(retweet);
		temp.setReplyTweet(replyTweets);
		tweetRepo.save(temp);
		return temp;
		
	}

}