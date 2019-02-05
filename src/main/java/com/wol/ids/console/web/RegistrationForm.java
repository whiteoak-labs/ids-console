package com.wol.ids.console.web;

import javax.validation.constraints.NotNull;

public class RegistrationForm 
{
	@NotNull
	protected String firstName;
	
	@NotNull
	protected String lastName;
	
	@NotNull
	protected String email;
	
	@NotNull
	protected String password;
	
	@NotNull
	protected String confirmedPassword;

	public RegistrationForm() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

}