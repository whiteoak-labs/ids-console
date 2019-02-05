package com.wol.ids.console.web;

import javax.validation.constraints.NotNull;

public class LoginForm
{
	@NotNull
	protected String email;

	@NotNull
	protected String password;

	protected boolean rememberMe = false;

	public LoginForm()
	{
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isRememberMe()
	{
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe)
	{
		this.rememberMe = rememberMe;
	}

}