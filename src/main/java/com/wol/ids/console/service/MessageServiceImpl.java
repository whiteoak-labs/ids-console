package com.wol.ids.console.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.ids.console.dao.MessageDao;
import com.wol.ids.console.model.Message;

@Service
public class MessageServiceImpl implements MessageService
{
	@Autowired
	private MessageDao messageDao;

	@Override
	public Message find(long id)
	{
		return messageDao.find(id);
	}

	@Override
	public Set<Message> findAll()
	{
		return messageDao.findAll();
	}

	@Override
	public Set<Message> findByRecipientEmail(String recipientEmail)
	{
		return messageDao.findByRecipientEmail(recipientEmail);
	}

	@Override
	public Set<Message> findUnread()
	{
		return messageDao.findUnread();
	}

	@Override
	public Message save(Message message)
	{
		return messageDao.save(message);
	}

	@Override
	public Message delete(Message message)
	{
		return messageDao.delete(message);
	}

	@Override
	public int deleteAll()
	{
		return messageDao.deleteAll();
	}

	@Override
	public Set<Message> filterUnreadByRecipientEmail(String recipientEmail)
	{
		Set<Message> unreadMsgs = findUnread();
		Set<Message> recipientUnreadMsgs = new HashSet<Message>();

		for (Message unreadMsg : unreadMsgs)
		{
			if (recipientEmail.equals(unreadMsg.getRecipient().getEmail()))
			{
				recipientUnreadMsgs.add(unreadMsg);
			}
		}

		return recipientUnreadMsgs;
	}
}
