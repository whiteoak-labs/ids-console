package com.wol.ids.console.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wol.ids.console.model.User;
import com.wol.ids.console.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;
	
	/**
	 * Retrieves user details from underlying data source, H2 Database, utilizing the  
	 * User's email address property as the username.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		logger.info("Received loadUserByUsername request for email: '{}'", email);
		
		if(StringUtils.isBlank(email)) 
		{
			return null;
		}
		
		
		User user = userService.findByEmail(email);
		
		if(null != user) 
		{
			return getUserDetailsFromUser(user);
		}
		
		return null;
	}

	private UserDetails getUserDetailsFromUser(User user) 
	{
		return new UserPrincipal(user);
	}
}
