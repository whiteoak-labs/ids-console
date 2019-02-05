package com.wol.ids.console.service;

import java.util.Set;

import com.wol.ids.console.model.User;

public interface UserService
{
	public User find(long id);

	public User findByEmail(String email);

	public Set<User> findLoggedin();

	public User save(User user);

	public User delete(User user);

	public User create(String firstName, String lastName, String password, String email);

	public void login(User user);

	public void logout(User user);
}
