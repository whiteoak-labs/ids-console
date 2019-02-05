package com.wol.ids.console.dao;

import java.util.Set;

import com.wol.ids.console.model.Message;

public interface MessageDao extends BasicDao<Message>
{

	public Set<Message> findByRecipientEmail(String recipientEmail);

	public Set<Message> findUnread();
}
