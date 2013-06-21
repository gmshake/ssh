package tk.blizz.ssh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.metadata.ClassMetadata;

import tk.blizz.ssh.dao.GenericDAO;
import tk.blizz.ssh.dao.GenericDaoException;

/**
 * @author zlei.huang@gmail.com 2013-05-25
 * @param <T>
 *            Entity class
 * @param <PK>
 *            Entity primary key
 */
public abstract class GenericHibernateDAO<T, PK extends Serializable>
		implements GenericDAO<T, PK> {

	private final Class<T> declaredClass;
	private final Class<? extends T> entityClass;
	private final String entityName;

	private transient SessionFactory sessionFactory;

	// @SuppressWarnings("unchecked")
	// public GenericHibernateDAO() {
	// this.entityClass = (Class<? extends T>) ((ParameterizedType) getClass()
	// .getGenericSuperclass()).getActualTypeArguments()[0];
	// }

	/**
	 * Constructor used for subclasses/implementors
	 * 
	 * @param entityClass
	 *            The class, which is an entity, or has entity
	 *            subclasses/implementors
	 * @throws NullPointerException
	 *             if entityClass is null
	 */
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO(Class<? extends T> entityClass) {
		this.declaredClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityClass = entityClass;
		this.entityName = entityClass.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) getSession().load(this.entityName, id);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws GenericDaoException
	 */
	private T createInstance(T entity) {
		Constructor<? extends T> constructor;
		try {
			constructor = this.entityClass.getConstructor(this.declaredClass);
			return constructor.newInstance(entity);
		} catch (SecurityException e) {
			throw new GenericDaoException(e);
		} catch (NoSuchMethodException e) {
			throw new GenericDaoException(e);
		} catch (IllegalArgumentException e) {
			throw new GenericDaoException(e);
		} catch (InstantiationException e) {
			throw new GenericDaoException(e);
		} catch (IllegalAccessException e) {
			throw new GenericDaoException(e);
		} catch (InvocationTargetException e) {
			throw new GenericDaoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		if (!this.entityClass.isAssignableFrom(entity.getClass())) {
			entity = createInstance(entity);
		}
		return (PK) getSession().save(this.entityName, entity);
	}

	public void persist(T entity) {
		if (!this.entityClass.isAssignableFrom(entity.getClass())) {
			entity = createInstance(entity);
		}
		getSession().persist(this.entityName, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(T persistentEntity) {
		getSession().update(this.entityName, persistentEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOrUpdate(T entity) {
		if (!this.entityClass.isAssignableFrom(entity.getClass())) {
			entity = createInstance(entity);
		}
		getSession().saveOrUpdate(this.entityName, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T merge(T detachedEntity) {
		return (T) getSession().merge(this.entityName, detachedEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T persistentEntity) {
		getSession().delete(this.entityName, persistentEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return createCriteria().list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) {
		return createCriteria().add(Example.create(example)).list();
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByExample(T example) {
		return (T) createCriteria().add(Example.create(example)).uniqueResult();
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
		NaturalIdLoadAccess access = getSession().byNaturalId(this.entityName);

		ClassMetadata meta = this.sessionFactory
				.getClassMetadata(this.entityName);
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
	 * Create {@link Criteria} instance for default entityName
	 * 
	 * @return
	 */
	protected Criteria createCriteria() {
		return getSession().createCriteria(this.entityName);
	}

	/**
	 * Create {@link Criteria} instance for the given entity name.
	 * 
	 * @param entityName
	 * @return
	 */
	protected Criteria createCriteria(String entityName) {
		return getSession().createCriteria(entityName);
	}

	/**
	 * Create a {@link Query} instance for the given HQL query string.
	 * 
	 * @param hql
	 *            a HQL string
	 * @return
	 */
	protected Query createQuery(String hql) {
		return getSession().createQuery(hql);
	}

	/**
	 * Create a {@link SQLQuery} instance for the given SQL query string.
	 * 
	 * @param sql
	 *            a Native SQL string
	 * @return The query instance for manipulation and execution
	 */
	protected SQLQuery createSQLQuery(String sql) {
		return getSession().createSQLQuery(sql);
	}

	/**
	 * get current session
	 * 
	 * @return The current session.
	 * @throws IllegalStateException
	 *             When sessionFactory is not set
	 * @throws HibernateException
	 *             Indicates an issue locating a suitable current session.
	 */
	protected Session getSession() {
		if (this.sessionFactory == null)
			throw new IllegalStateException("no SessionFactory found");
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
