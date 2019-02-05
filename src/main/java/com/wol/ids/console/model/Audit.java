package com.wol.ids.console.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Audit
{
	@Column(name = "created_at", updatable = false)
	@JsonProperty(value = "createdAt")
	private Date createdAt;
	
//	@Column(name = "created_by", updatable = false, nullable = true)
//	@JsonProperty(value = "createdBy")	
//	private User createdBy;

	@Column(name = "updated_at", updatable = false)
	@JsonProperty(value = "updatedAt")
	private Date updatedAt;
	
//	@Column(name = "updated_by", updatable = false)
//	@JsonProperty(value = "updatedBy")	
//	private User updatedBy;

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

//	public User getCreatedBy()
//	{
//		return createdBy;
//	}
//
//	public void setCreatedBy(User createdBy)
//	{
//		this.createdBy = createdBy;
//	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}

//	public User getUpdatedBy()
//	{
//		return updatedBy;
//	}
//
//	public void setUpdatedBy(User updatedBy)
//	{
//		this.updatedBy = updatedBy;
//	}
	
	
}
