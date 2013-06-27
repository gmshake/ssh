package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tk.blizz.ssh.dao.impl.GenericHibernateDAO;

/**
 * Tests for {@link tk.blizz.ssh.dao.impl.GenericHibernateDAO}.
 * 
 * @author zlei.huang@gmail.com (Huang Zhenlei)
 * 
 */
public class TestGenericHibernateDAO {
	public class TestDAOImpl extends GenericHibernateDAO<User, UserImpl, Long> {
		UserImpl findByName(String name) {
			return super.findUniqueByExample(new UserImpl().setName(name));
		}

		@Override
		public Session getSession() {
			return super.getSession();
		}
	}

	private final org.hsqldb.server.Server server = new org.hsqldb.server.Server();
	private final TestDAOImpl dao = new TestDAOImpl();

	EhCacheRegionFactory u;

	@Before
	public void setup() {
		this.server.setDatabaseName(0, "memdb");
		this.server.setDatabasePath(0, "mem:memdb");
		this.server.start();

		final Configuration configuration = new Configuration()
				.addAnnotatedClass(UserImpl.class).configure();
		final ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());

		final SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistryBuilder
						.buildServiceRegistry());

		this.dao.setSessionFactory(sessionFactory);
	}

	@After
	public void teardown() {
		final SessionFactory sessionFactory = this.dao.getSessionFactory();
		this.dao.setSessionFactory(null);
		sessionFactory.close();

		this.server.signalCloseAllServerConnections();
		this.server.stop();
		this.server.shutdown();
	}

	/**
	 * find by id should test handy, create a record in table first, follower
	 * test is based on this
	 */
	@Test
	public void testFindById() {
		assertTrue(true);
	}

	@Test
	public void testSaves() {
		final User u = new UserImpl().setName("hello").setCName("你好");
		assertTrue(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.save(u);
				return true;
			}

		}.run());

		/**
		 * unique constraint on 'name'
		 */
		assertFalse(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.save(u);
				return true;
			}

		}.run());

		assertTrue(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.save(u.setName("world"));
				return true;
			}

		}.run());

		assertTrue(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.merge(u);
				return true;
			}

		}.run());

		assertFalse(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.save(u);
				return true;
			}

		}.run());

		assertFalse(new DaoWrapper<TestDAOImpl>(this.dao) {

			@Override
			protected boolean go() {
				this.dao.update(u);
				return true;
			}

		}.run());
	}

	@Test
	public void testSave() throws Exception {
		assertTrue(new DaoWrapper<TestDAOImpl>(this.dao) {
			@Override
			protected boolean go() {
				User e = new UserImpl();
				e.setName("Hello");
				e.setCName("你好");
				this.dao.save(e);

				User f = this.dao.findById(e.getId());
				assertTrue(f != null);
				assertTrue(e.getName().equals(f.getName()));
				assertTrue(e.getCName().equals(f.getCName()));

				System.out.println(f);

				f.setName("test");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				this.dao.update(f);
				System.out.println(f);

				User t = new User() {
					private Long id;
					private String name;
					private String cname;

					@Override
					public User setName(String name) {
						this.name = name;
						return this;
					}

					@Override
					public User setId(Long id) {
						this.id = id;
						return this;
					}

					@Override
					public User setCName(String cname) {
						this.cname = cname;
						return this;
					}

					@Override
					public String getName() {
						return this.name;
					}

					@Override
					public Long getId() {
						return this.id;
					}

					@Override
					public String getCName() {
						return this.cname;
					}
				}.setName("test").setCName("测试");

				this.dao.persist(t);

				// dao.update(new UserImpl().setId(3L));

				// dao.update(t);

				// System.out.println(t);

				User u = new UserImpl() {
				}.setName("test2").setCName("测试2");
				this.dao.save(u);
				u.setName("test22");
				this.dao.save(u);
				u.setName("test222");
				this.dao.persist(u);

				System.out.println(u);

				User uu = this.dao.findByName("test");
				System.out.println(uu);

				User uu2 = this.dao.findById(uu.getId());
				System.out.println(uu2);

				return true;
			}
		}.run());

		new DaoWrapper<GenericHibernateDAO<?, ?, ?>>(this.dao) {

			@Override
			protected boolean go() {
				TestDAOImpl dao = (TestDAOImpl) this.dao;

				List<UserImpl> us = dao.findAll();
				for (UserImpl u : us) {
					System.out.println(u);
				}
				return true;
			}
		}.run();
	}
}
