package com.wol.ids.console.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "sc_user_message")
public class Message
{

	@JsonIgnore
	@Id
	@SequenceGenerator(name = "seq_sc_user", initialValue = 1, sequenceName = "seq_sc_user")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sc_user")
	@Column(nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn
	@JsonProperty
	private User recipient;

	@ManyToOne
	@JoinColumn
	@JsonProperty
	private User sender;

	@Column
	@JsonProperty
	private Date sent;

	@Column
	@JsonProperty
	private String text;

	@Column
	@JsonProperty
	private boolean read;

	public Message()
	{
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public User getSender()
	{
		return sender;
	}

	public void setSender(User sender)
	{
		this.sender = sender;
	}

	public Date getSent()
	{
		return sent;
	}

	public void setSent(Date sent)
	{
		this.sent = sent;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isRead()
	{
		return read;
	}

	public void setRead(boolean read)
	{
		this.read = read;
	}

	public User getRecipient()
	{
		return recipient;
	}

	public void setRecipient(User recipient)
	{
		this.recipient = recipient;
	}

	public String toString()
	{
		return new StringBuilder().append("Message[").append("sender=").append(sender.getEmail())
		        .append(", recipient=").append(recipient.getEmail()).append(", sent=").append(sent)
		        .toString();
	}
}
