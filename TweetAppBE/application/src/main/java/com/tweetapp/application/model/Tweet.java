package com.tweetapp.application.model;


import java.util.List;

//import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document
public class Tweet {
	@Id
//	@JsonSerialize(using=ObjectIdSerializer.class)************
	private String id;
	private String email;
	private String tweetMsg;
	private String time;
	private int like;
	private String tagText;
	private List<Tweet> replyTweet;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTweetMsg() {
		return tweetMsg;
	}
	public void setTweetMsg(String tweetMsg) {
		this.tweetMsg = tweetMsg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getTagText() {
		return tagText;
	}
	public void setTagText(String tagText) {
		this.tagText = tagText;
	}
	public List<Tweet> getReplyTweet() {
		return replyTweet;
	}
	public void setReplyTweet(List<Tweet> replyTweet) {
		this.replyTweet = replyTweet;
	}
	public Tweet(
			String id,
			String email, String tweetMsg, String time, int like, String tagText, List<Tweet> replyTweet) {
		super();
		this.id = id;
		this.email = email;
		this.tweetMsg = tweetMsg;
		this.time = time;
		this.like = like;
		this.tagText = tagText;
		this.replyTweet = replyTweet;
	}
	@Override
	public String toString() {
		return "Tweet [id=" + id + ", email=" + email + ", tweetMsg=" + tweetMsg + ", time=" + time + ", like=" + like
				+ ", tagText=" + tagText + ", replyTweet=" + replyTweet + "]";
	}
	
}

