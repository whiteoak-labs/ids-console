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

import com.wol.ids.console.model.Message;

@Repository
public class MessageDaoImpl implements MessageDao
{

	private static Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public Message find(Long id)
	{
		Query findQuery = entityManager.createQuery("FROM Message where id=:id");
		findQuery.setParameter("id", id);
		Message msg = null;

		try
		{
			msg = (Message) findQuery.getSingleResult();
		}
		catch (NoResultException e)
		{
		}

		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Message> findAll()
	{
		Query findQuery = entityManager.createQuery("From Message");
		return new HashSet<Message>(findQuery.getResultList());
	}

	@Override
	public Set<Message> findByRecipientEmail(String recipientEmail)
	{
		Set<Message> messages = findAll();
		Set<Message> recipientMsgs = new HashSet<Message>();

		for (Message message : messages)
		{
			if (recipientEmail.equals(message.getRecipient().getEmail()))
			{
				recipientMsgs.add(message);
			}
		}
		
		return recipientMsgs;
	}

	@Override
	public Set<Message> findUnread()
	{
		Set<Message> messages = findAll();
		Set<Message> unreadMsgs = new HashSet<Message>();

		for (Message message : messages)
		{
			if (!message.isRead())
			{
				unreadMsgs.add(message);
			}
		}

		logger.debug("User has " + unreadMsgs.size() + " unread messages");
		return unreadMsgs;
	}

	@Override
	@Transactional
	public Message save(Message message)
	{
		entityManager
		        .persist(entityManager.contains(message) ? message : entityManager.merge(message));
		entityManager.flush();
		logger.debug("Successfully saved " + message);
		return message;
	}

	@Override
	@Transactional
	public Message delete(Message message)
	{
		entityManager
		        .remove(entityManager.contains(message) ? message : entityManager.merge(message));
		entityManager.flush();
		logger.debug("Successfully deleted " + message);
		return message;
	}

	@Override
	public int deleteAll()
	{
		Query countQuery = entityManager.createQuery("DELETE FROM Message");
		int count = countQuery.executeUpdate();
		logger.debug("Successfully deleted " + count + " messages");
		return count;
	}

}
