package com.wol.ids.console.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wol.ids.console.Utils;
import com.wol.ids.console.model.User;

public class UserPrincipal implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserPrincipal(User user)
	{
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword()
	{
		return Utils.Crypto.decrypt(user.getPassword());
	}

	@Override
	public String getUsername()
	{
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return !this.user.isExpired();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return !this.user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return !this.user.isExpired();
	}

	@Override
	public boolean isEnabled()
	{
		return this.user.isEnabled();
	}

}
