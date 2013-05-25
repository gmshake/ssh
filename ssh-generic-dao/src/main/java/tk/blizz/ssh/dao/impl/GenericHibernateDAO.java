package tk.blizz.ssh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import tk.blizz.ssh.dao.GenericDAO;

public abstract class GenericHibernateDAO<T, PK extends Serializable>
		implements GenericDAO<T, PK> {
	private final Class<T> persistentClass;

	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * find element by primary key
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) getSession().load(this.persistentClass, id);
	}

	/**
	 * persistent element
	 * 
	 * @param entity
	 */
	public void save(T entity) {
		getSession().save(entity);
	}

	/**
	 * update persistent entity
	 * 
	 * @param persistentEntity
	 */
	public void update(T persistentEntity) {
		getSession().update(persistentEntity);
	}

	/**
	 * 
	 */
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * delete persistent entity
	 * 
	 * @param persistentEntity
	 */
	public void delete(T persistentEntity) {
		getSession().delete(persistentEntity);
	}

	/**
	 * find all elements
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * find any elements by given example instance
	 * 
	 * @param example
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) {
		return createCriteria().add(Example.create(example)).list();
	}

	/**
	 * get new Criteria
	 * 
	 * @return
	 */
	protected Criteria createCriteria() {
		return getSession().createCriteria(this.persistentClass);
	}

	/**
	 * get current session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	// getters and setters
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
