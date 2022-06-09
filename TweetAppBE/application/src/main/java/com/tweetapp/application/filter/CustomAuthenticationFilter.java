package com.tweetapp.application.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
		this.authenticationManager = authenticationManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
//		System.out.println(request);
		String username = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println(username +" " + password);
		UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Executed Unsuccessful authentication for once");
		Map<String, String> result = new HashMap<>();
		result.put("code", "404");
		result.put("Description", "User not found.");
		new ObjectMapper().writeValue(response.getOutputStream(), result);
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		logger.info("User logged in successfully");
		User user =(User) authentication.getPrincipal(); 
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+40*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		Map<String, String> result = new HashMap<>();
		result.put("access_token", access_token);
		result.put("code", "200");
		result.put("username", user.getUsername());
		
		new ObjectMapper().writeValue(response.getOutputStream(), result);
	}

	
}
