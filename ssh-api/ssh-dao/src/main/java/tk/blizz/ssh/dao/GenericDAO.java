package tk.blizz.ssh.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDAO<PK extends Serializable, E> {
	/**
	 * find element by primary key
	 * 
	 * @param id
	 * @return
	 */
	E findById(PK id);

	/**
	 * persistent element
	 * 
	 * @param e
	 */
	void save(E e);

	/**
	 * update element
	 * 
	 * @param e
	 */
	void update(E e);

	/**
	 * delete element
	 * 
	 * @param e
	 */
	void delete(E e);

	/**
	 * find all elements
	 * 
	 * @return
	 */
	List<E> findAll();

	/**
	 * find any elements by given example
	 * 
	 * @param e
	 * @return
	 */
	List<E> findByExample(E e);

	/**
	 * 
	 * @param map
	 * @return
	 */
	List<E> findByProperties(Map<String, Object> map);

}
