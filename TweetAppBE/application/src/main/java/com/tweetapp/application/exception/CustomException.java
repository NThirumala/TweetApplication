package com.tweetapp.application.exception;


public class CustomException extends RuntimeException{
		public String message;
		
		public CustomException(String message) {
	        super(message);
	        this.message = message;
	    }
	    public CustomException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}
		public CustomException() {
	    }
}
