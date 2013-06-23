package tk.blizz.ssh.dao;

import java.io.Serializable;
import java.util.List;

/**
 * GenericDAO CRUD
 * 
 * @author zlei.huang@gmail.com 2013-05-25
 * 
 * @param <T>
 *            Entity class
 * @param <PK>
 *            Entity primary key
 */
public interface GenericDAO<T, PK extends Serializable> {
	/**
	 * find entity by primary key
	 * 
	 * @param id
	 * @return
	 */
	T findById(PK id);

	/**
	 * save transient instance
	 * 
	 * @param entity
	 */
	PK save(T entity);

	/**
	 * update entity
	 * 
	 * @param entity
	 */
	void update(T persistentEntity);

	/**
	 * save if entity not persisted, or update otherwise
	 * 
	 * @param entity
	 */
	void saveOrUpdate(T entity);

	/**
	 * merge detached entity
	 * 
	 * @param detachedEntity
	 * @return
	 */
	T merge(T detachedEntity);

	/**
	 * delete entity
	 * 
	 * @param entity
	 */
	void delete(T persistentEntity);

	/**
	 * find all entities
	 * 
	 * @return
	 */
	List<? extends T> findAll();

	/**
	 * find any entity by given entity example
	 * 
	 * @param example
	 * @return
	 */
	List<? extends T> findByExample(T example);

}
