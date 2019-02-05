package com.wol.ids.console.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.ids.console.Utils;
import com.wol.ids.console.dao.UserDao;
import com.wol.ids.console.model.User;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	private UserDao userDao;

	// @Autowired
	// private SessionService sessionService;

	@PostConstruct
	public void createAdminUser()
	{
		User adminUser = findByEmail("admin.ids@whiteoaklabs.com");

		if (adminUser == null)
		{
			adminUser = create("IDS", "Console", "master", "admin.ids@whiteoaklabs.com");
			save(adminUser);
		}
	}

	@Override
	public User find(long id)
	{
		return userDao.find(id);
	}

	@Override
	public User findByEmail(String email)
	{
		return userDao.findByEmail(email);
	}

	@Override
	public Set<User> findLoggedin()
	{
		Set<User> allUsers = userDao.findAll();
		if (allUsers == null || allUsers.isEmpty())
		{
			return allUsers;
		}

		Set<User> loggedInUsers = new HashSet<User>();
		for (User user : allUsers)
		{
			// if(User.State.LOGGED_IN.equals(user.getState())) {
			loggedInUsers.add(user);
			// }
		}

		return loggedInUsers;
	}

	@Override
	public User save(User user)
	{
		return userDao.save(user);
	}

	@Override
	public User delete(User user)
	{
		return userDao.delete(user);
	}

	@Override
	public User create(String firstName, String lastName, String password, String email)
	{
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(Utils.Crypto.encrypt(password));
		return user;
	}

	@Override
	public void login(User user)
	{
		// user.setState(User.State.LOGGED_IN);
		user.setLastLogin(new Date());
		// user.setSession(sessionService.create());
		save(user);
	}

	@Override
	public void logout(User user)
	{
		// user.setState(User.State.LOGGED_OUT);
		save(user);
	}

}
