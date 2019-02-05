package com.wol.ids.console.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wol.ids.console.model.User;

@Repository
public class UserDaoImpl implements UserDao
{

	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public User find(Long id)
	{
		Query findQuery = entityManager.createQuery("FROM User WHERE id=:id");
		findQuery.setParameter("id", id);
		User user = null;

		try
		{
			user = (User) findQuery.getSingleResult();
		}
		catch (NoResultException e)
		{
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<User> findAll()
	{
		Query findQuery = entityManager.createQuery("FROM User");
		return new HashSet<User>(findQuery.getResultList());
	}

	@Override
	public User findByEmail(String email)
	{
		Query findQuery = entityManager.createQuery("From User WHERE email=:email");
		findQuery.setParameter("email", email);
		User user = null;

		try
		{
			user = (User) findQuery.getSingleResult();
		}
		catch (NoResultException e)
		{
		}

		return user;
	}

	@Override
	@Transactional
	public User save(User user)
	{
		entityManager.persist(entityManager.contains(user) ? user : entityManager.merge(user));
		entityManager.flush();
		logger.debug("Successfully saved " + user);
		return user;
	}

	@Override
	@Transactional
	public User delete(User user)
	{
		entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
		entityManager.flush();
		logger.debug("Successfully deleted " + user);
		return user;
	}

	@Override
	@Transactional
	public int deleteAll()
	{
		Query deleteQuery = entityManager.createQuery("DELETE FROM User");
		int count =  deleteQuery.executeUpdate();
		entityManager.flush();
		logger.debug("Successfully deleted '{}' users", count);
		return count;
	}
}
