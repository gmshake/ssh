package tk.blizz.ssh.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import tk.blizz.ssh.dao.GenericDAO;

public abstract class GenericHibernateDAO<PK extends Serializable, E>
		implements GenericDAO<PK, E> {
	/**
	 * find element by primary key
	 * 
	 * @param id
	 * @return
	 */
	public E findById(PK id) {
		return null;
	}

	/**
	 * persistent element
	 * 
	 * @param e
	 */
	public void save(E e) {
	}

	/**
	 * update element
	 * 
	 * @param e
	 */
	public void update(E e) {
	}

	/**
	 * delete element
	 * 
	 * @param e
	 */
	public void delete(E e) {
	}

	/**
	 * find all elements
	 * 
	 * @return
	 */
	public List<E> findAll() {
		return null;
	}

	/**
	 * find any elements by given example
	 * 
	 * @param e
	 * @return
	 */
	public List<E> findByExample(E e) {
		return null;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<E> findByProperties(Map<String, Object> map) {
		return null;
	}
}
