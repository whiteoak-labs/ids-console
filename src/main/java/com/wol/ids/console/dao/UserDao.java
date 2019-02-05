package com.wol.ids.console.dao;

import com.wol.ids.console.model.User;;

public interface UserDao extends BasicDao<User>
{
	public User findByEmail(String email);
}
