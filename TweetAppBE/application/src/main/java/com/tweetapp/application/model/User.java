package com.tweetapp.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	@Id
	private String id;
	private String firstName;
	private String lastname;
	private String gender;
	private String dob;
	@Indexed(unique=true)
	private String email;
	private String password;
	private String contactnumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	public User(String id, String firstName, String lastname, String gender, String dob, String email, String password,
			String contactnumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastname = lastname;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.password = password;
		this.contactnumber = contactnumber;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastname=" + lastname + ", gender=" + gender
				+ ", dob=" + dob + ", email=" + email + ", password=" + password + ", contactnumber=" + contactnumber
				+ "]";
	}
}
