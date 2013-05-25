package tk.blizz.ssh.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
	/**
	 * find entity by primary key
	 * 
	 * @param id
	 * @return
	 */
	T findById(PK id);

	/**
	 * persist entity
	 * 
	 * @param entity
	 */
	void save(T entity);

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
	List<T> findAll();

	/**
	 * find any entity by given entity example
	 * 
	 * @param example
	 * @return
	 */
	List<T> findByExample(T example);
}
