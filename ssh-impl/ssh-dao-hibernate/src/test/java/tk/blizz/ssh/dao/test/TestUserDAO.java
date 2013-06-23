package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tk.blizz.ssh.dao.impl.UserDAOImpl;
import tk.blizz.ssh.model.User;

/**
 * Tests for {@link tk.blizz.ssh.dao.impl.UserDAOImpl}.
 * 
 * @author zlei.huang@gmail.com (Huang Zhenlei)
 * 
 */
public class TestUserDAO {

	org.hsqldb.server.Server server;
	private SessionFactory sessionFactory;
	private final UserDAOImpl userDao = new UserDAOImpl();

	private Transaction trans;

	private boolean hasError;

	@Before
	public void setup() {
		this.server = new org.hsqldb.server.Server();
		this.server.setDatabaseName(0, "memdb");
		this.server.setDatabasePath(0, "mem:memdb");
		this.server.start();

		final Configuration configuration = new Configuration().configure();
		final ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());

		this.sessionFactory = configuration
				.buildSessionFactory(serviceRegistryBuilder
						.buildServiceRegistry());

		this.userDao.setSessionFactory(this.sessionFactory);

		this.trans = this.sessionFactory.getCurrentSession().beginTransaction();
	}

	@After
	public void teardown() {
		if (this.hasError)
			this.trans.rollback();
		else
			this.trans.commit();
		this.userDao.setSessionFactory(null);
		this.sessionFactory.close();

		this.server.shutdown();

	}

	@Test
	public void testSave() throws Exception {
		try {
			User u = new User();
			u.setName("Hello");
			u.setBirthday(new Date());
			this.userDao.save(u);

			User f = this.userDao.findById(u.getId());
			assertTrue(f != null);
			assertTrue(u.getName().equals(f.getName()));
			assertTrue(u.getBirthday().equals(f.getBirthday()));
		} catch (Exception e) {
			this.hasError = true;
			throw e;
		}
	}
}
