package com.wol.ids.console.dao;

import java.util.Set;

/**
 * Data access object used for dealing with persistables
 * 
 * @author tcook
 * @see T
 */
public interface BasicDao<T>
{
	/**
	 * Attempts to local an entity in persistent
	 * 
	 * @param id
	 * @return T with matching primary key, null if not found
	 * @see T
	 */
	public T find(Long id);

	/**
	 * Retrieve all entitys in persistent storage
	 * 
	 * @return Set of entitys in persistent storage
	 * @see Set
	 * @see T
	 */
	public Set<T> findAll();

	/**
	 * Upsert an entity into persistent storage.
	 * 
	 * @param entity
	 *            to persist
	 * @return T which was persisted
	 * @see T
	 */
	public T save(T entity);

	/**
	 * Delete a given entity from persistent storage.
	 * 
	 * @param entity
	 *            to delete
	 * @return T which was deleted
	 * @see T
	 */
	public T delete(T entity);

	/**
	 * Deletes all entitys from persistent storage
	 * 
	 * @return count of all entitys deleted
	 * @see T
	 */
	public int deleteAll();

}
