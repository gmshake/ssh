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
 *            Declared Type
 * @param <E>
 *            Entity Real Type
 * 
 * @param <PK>
 *            Entity primary key
 */
public abstract class GenericHibernateDAO<T, E extends T, PK extends Serializable>
		implements GenericDAO<T, PK> {

	private final Class<T> declaredClass;
	private final Class<E> entityClass;
	private final Class<PK> pkClass;

	private final String entityName;

	private transient SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.declaredClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		this.pkClass = (Class<PK>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[2];
		this.entityName = this.entityClass.getName();
	}

	/**
	 * Constructor used for subclasses/implementors
	 * 
	 * @param entityClass
	 *            The class, which is an entity, or has entity
	 *            subclasses/implementors
	 * @throws NullPointerException
	 *             if entityClass is null
	 */
	// @SuppressWarnings("unchecked")
	// public GenericHibernateDAO(Class<? extends T> entityClass) {
	// this.declaredClass = (Class<T>) ((ParameterizedType) getClass()
	// .getGenericSuperclass()).getActualTypeArguments()[0];
	// this.entityClass = entityClass;
	// this.entityName = entityClass.getName();
	// }

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E findById(PK id) {
		return (E) getSession().get(this.entityName, id);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws GenericDaoException
	 */
	private E createInstance(T entity) {
		try {
			Constructor<E> constructor = this.entityClass
					.getConstructor(this.declaredClass);
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
	public PK save(T entity) {
		if (!this.entityClass.isAssignableFrom(entity.getClass())) {
			entity = createInstance(entity);
		}
		return this.pkClass.cast(getSession().save(this.entityName, entity));
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
		E p = this.entityClass.cast(persistentEntity);
		getSession().update(this.entityName, p);
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
	@SuppressWarnings("unchecked")
	@Override
	public E merge(T detachedEntity) {
		// E p = this.entityClass.cast(detachedEntity);
		T p = detachedEntity;
		return (E) getSession().merge(this.entityName, p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T persistentEntity) {
		// check entity class
		E p = this.entityClass.cast(persistentEntity);
		getSession().delete(this.entityName, p);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return createCriteria().list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findByExample(T example) {
		return createCriteria().add(Example.create(example)).list();
	}

	@SuppressWarnings("unchecked")
	public E findUniqueByExample(T example) {
		return (E) createCriteria().add(Example.create(example)).uniqueResult();
	}

	/**
	 * find entity by natural id
	 * 
	 * @param entity
	 *            an example entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected E findByNaturalId(T entity) {
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

			return (E) access.load();
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
