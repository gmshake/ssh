package tk.blizz.ssh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.metadata.ClassMetadata;

import tk.blizz.ssh.dao.GenericDAO;

/**
 * @author zlei.huang@gmail.com 2013-05-25
 * @param <T>
 *            Entity class
 * @param <PK>
 *            Entity primary key
 */
public abstract class GenericHibernateDAO<T, PK extends Serializable>
		implements GenericDAO<T, PK> {
	private static final long serialVersionUID = 2013052702L;

	private final Class<T> entityClass;

	private transient SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * find element by primary key
	 * 
	 * @see GenericDAO#findById(Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) getSession().load(this.entityClass, id);
	}

	/**
	 * persistent element
	 * 
	 * @see GenericDAO#save(Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		return (PK) getSession().save(entity);
	}

	/**
	 * update persistent entity
	 * 
	 * @see GenericDAO#update(Object)
	 */
	@Override
	public void update(T persistentEntity) {
		getSession().update(persistentEntity);
	}

	/**
	 * @see GenericDAO#saveOrUpdate(Object)
	 */
	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * @see GenericDAO#merge(Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T detachedEntity) {
		return (T) getSession().merge(detachedEntity);
	}

	/**
	 * delete persistent entity
	 * 
	 * @see GenericDAO#delete(Object)
	 */
	@Override
	public void delete(T persistentEntity) {
		getSession().delete(persistentEntity);
	}

	/**
	 * find all elements
	 * 
	 * @see GenericDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * find any elements by given example instance
	 * 
	 * @see GenericDAO#findByExample(Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) {
		return createCriteria().add(Example.create(example)).list();
	}

	/**
	 * find entity by natural id
	 * 
	 * @param entity
	 *            an example entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T findByNaturalId(T entity) {
		NaturalIdLoadAccess access = getSession().byNaturalId(this.entityClass);

		ClassMetadata meta = this.sessionFactory
				.getClassMetadata(this.entityClass);
		int[] idxs = meta.getNaturalIdentifierProperties();

		if (idxs != null && idxs.length > 0) {

			try {
				for (int idx : idxs) {
					String name = meta.getPropertyNames()[idx];
					Object value = PropertyUtils.getProperty(entity, name);

					access.using(name, value);

				}
			} catch (IllegalAccessException e) {
				throw new HibernateException(e);
			} catch (InvocationTargetException e) {
				throw new HibernateException(e);
			} catch (NoSuchMethodException e) {
				throw new HibernateException(e);
			}

			return (T) access.load();
		}
		return null;
	}

	/**
	 * get new Criteria
	 * 
	 * @return
	 */
	protected Criteria createCriteria() {
		return getSession().createCriteria(this.entityClass);
	}

	/**
	 * get new Query from given hql
	 * 
	 * @param hql
	 *            a HQL string
	 * @return
	 */
	protected Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}

	/**
	 * get new Query from given sql
	 * 
	 * @param sql
	 *            a Native SQL string
	 * @return
	 */
	protected Query createSQLQuery(String sql) {
		return getSession().createSQLQuery(sql);
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
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
