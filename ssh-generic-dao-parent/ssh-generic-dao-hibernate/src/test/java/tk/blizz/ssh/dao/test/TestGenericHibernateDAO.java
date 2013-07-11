package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	private final Configuration configuration = new Configuration()
			.addAnnotatedClass(UserImpl.class).configure();
	private final ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
			.applySettings(configuration.getProperties());

	private final SessionFactory sessionFactory = configuration
			.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());

	public class TestDAOImpl extends GenericHibernateDAO<User, UserImpl, Long> {
		UserImpl findByName(String name) {
			return super.findUniqueByExample(new UserImpl().setName(name));
		}

		@Override
		public Session getSession() {
			return super.getSession();
		}
	}

	private final TestDAOImpl dao = new TestDAOImpl();

	private final TransactionWrapper wrapper = new TransactionWrapper(
			this.sessionFactory);

	@Before
	public void setup() {
		this.dao.setSessionFactory(sessionFactory);
	}

	@After
	public void teardown() {
		// final SessionFactory sessionFactory = this.dao.getSessionFactory();
		this.dao.setSessionFactory(null);
		// sessionFactory.close();
	}

	/**
	 * find by id should test handy, create a record in table first, follower
	 * test is based on this
	 */
	@Test
	public void testFindById() {
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(new UserImpl().setName("Hello").setCName("World"));
				return true;
			}
		}));

		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				UserImpl u = dao.findById(1L);
				System.out.println(u);
				return "Hello".equals(u.getName());
			}
		}));
	}

	@Test
	public void testFindById2() {
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(new UserImpl().setName("Hello").setCName("World"));
				return true;
			}
		}));

		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				UserImpl u = dao.findById(1L);
				System.out.println(u);
				return "Hello".equals(u.getName());
			}
		}));
	}

	@Test
	public void testFindById3() {
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(new UserImpl().setName("Hello").setCName("World"));
				return true;
			}
		}));

		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				UserImpl u = dao.findById(1L);
				System.out.println(u);
				return "Hello".equals(u.getName());
			}
		}));
	}

	@Test
	public void testSaves() {
		final User u = new UserImpl().setName("hello").setCName("你好");
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(u);
				return true;
			}

		}));

		/**
		 * unique constraint on 'name'
		 */
		assertFalse(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(u);
				return true;
			}

		}));

		/**
		 * another instance
		 */
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(u.setName("world"));
				return true;
			}
		}));

		/**
		 * merge
		 */
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.merge(u);
				return true;
			}
		}));

		/**
		 * unique constraint on 'name'
		 */
		assertFalse(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.save(u);
				return true;
			}
		}));

		/**
		 * update detached instance
		 */
		assertFalse(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				dao.update(u);
				return true;
			}
		}));
	}

	@Test
	public void testSave() throws Exception {
		assertTrue(this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				User e = new UserImpl();
				e.setName("Hello");
				e.setCName("你好");
				dao.save(e);

				User f = dao.findById(e.getId());
				assertTrue(f != null);
				assertTrue(e.getName().equals(f.getName()));
				assertTrue(e.getCName().equals(f.getCName()));

				System.out.println(f);

				f.setName("test");
				dao.update(f);
				System.out.println(f);

				User t = new User() {
					private static final long serialVersionUID = 1L;
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

				dao.persist(t);

				// dao.update(new UserImpl().setId(3L));

				// dao.update(t);

				// System.out.println(t);

				User u = new UserImpl() {
				}.setName("test2").setCName("测试2");
				dao.save(u);
				u.setName("test22");
				dao.save(u);
				u.setName("test222");
				dao.persist(u);

				System.out.println(u);

				User uu = dao.findByName("test");
				System.out.println(uu);

				User uu2 = dao.findById(uu.getId());
				System.out.println(uu2);

				return true;
			}
		}));

		this.wrapper.run(new Block() {
			@Override
			public boolean execute() {
				List<UserImpl> us = dao.findAll();
				for (UserImpl u : us) {
					System.out.println(u);
				}
				return true;
			}
		});
	}
}
