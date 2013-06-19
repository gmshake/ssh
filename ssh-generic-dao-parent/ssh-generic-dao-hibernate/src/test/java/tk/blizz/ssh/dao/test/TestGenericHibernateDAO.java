package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
	class TestDAOImpl extends GenericHibernateDAO<Event, Long> {
		public TestDAOImpl() {
			super(Event.class);
		}
	}

	org.hsqldb.server.Server server;
	private SessionFactory sessionFactory;
	private TestDAOImpl testDao;

	private Transaction trans;

	private boolean hasError;

	@Before
	public void setup() {
		this.server = new org.hsqldb.server.Server();
		this.server.setDatabaseName(0, "memdb");
		this.server.setDatabasePath(0, "mem:memdb");
		this.server.start();

		this.sessionFactory = new Configuration().configure()
				.buildSessionFactory();

		this.testDao = new TestDAOImpl();
		this.testDao.setSessionFactory(this.sessionFactory);

		this.trans = this.sessionFactory.getCurrentSession().beginTransaction();
	}

	@After
	public void teardown() {
		if (this.hasError)
			this.trans.rollback();
		else
			this.trans.commit();
		this.testDao.setSessionFactory(null);
		this.sessionFactory.close();

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
	public void testSave() throws Exception {
		try {
			Event e = new Event();
			e.setName("Hello");
			e.setCreateTime(new Date());
			this.testDao.save(e);

			Event f = this.testDao.findById(e.getId());
			assertTrue(f != null);
			assertTrue(e.getName().equals(f.getName()));
			assertTrue(e.getCreateTime().equals(f.getCreateTime()));
		} catch (Exception e) {
			this.hasError = true;
			throw e;
		}
	}

}
