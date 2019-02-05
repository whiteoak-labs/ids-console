package com.wol.ids.console.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "sc_user")
public class User
{
	@JsonIgnore
	@Id
	@SequenceGenerator(name = "seq_sc_user", initialValue = 1, sequenceName = "seq_sc_user")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sc_user")
	@Column(nullable = false)
	private long id;
	
	@Column(nullable = false)
	@NotNull
	@JsonProperty
	private String email;
	
	@Column
	@JsonProperty
	private String firstName;
	
	@Column
	@JsonProperty	
	private String lastName;
	
	@Column
	@JsonProperty
	private String phoneNumber;
	
	@Column
	@JsonProperty
	private String password;
	
	@Column
	@JsonProperty
	private Date lastLogin;
	
	@Column
	@JsonProperty
	private boolean enabled = true;
	
	@Column
	@JsonProperty
	private boolean expired = true;
	
	@Column
	@JsonProperty
	private boolean locked = false;
	
	@Embedded
	private Audit audit = new Audit();
	
	public User(){}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Date getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin)
	{
		this.lastLogin = lastLogin;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isExpired()
	{
		return expired;
	}

	public void setExpired(boolean expired)
	{
		this.expired = expired;
	}

	public boolean isLocked()
	{
		return locked;
	}

	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}

	public Audit getAudit()
	{
		return audit;
	}

	public void setAudit(Audit audit)
	{
		this.audit = audit;
	}

	@Override
	public String toString()
	{
		return "User [email=" + email + ", firstName=" 
	+ firstName + ", lastName=" + lastName + "]";
	}
	
}
