package tk.blizz.ssh.service.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tk.blizz.ssh.dao.impl.RoleDAOImpl;
import tk.blizz.ssh.dao.impl.UserDAOImpl;
import tk.blizz.ssh.model.User;
import tk.blizz.ssh.service.impl.AuthenticationServiceImpl;
import tk.blizz.ssh.service.impl.SystemManageServiceImpl;

/**
 * test case for service
 * 
 * @author zlei.huang@gmail.com
 * 
 */
public class AuthenticationServiceTest {
	org.hsqldb.server.Server server;
	private SessionFactory sessionFactory;

	private final AuthenticationServiceImpl auth = new AuthenticationServiceImpl();

	private final SystemManageServiceImpl sysm = new SystemManageServiceImpl();

	@Before
	public void setup() {
		// setup hsqldb
		this.server = new org.hsqldb.server.Server();
		this.server.setDatabaseName(0, "memdb");
		this.server.setDatabasePath(0, "mem:memdb");
		this.server.start();

		this.sessionFactory = new Configuration().configure()
				.buildSessionFactory();

		UserDAOImpl userDao = new UserDAOImpl();
		RoleDAOImpl roleDao = new RoleDAOImpl();

		userDao.setSessionFactory(this.sessionFactory);
		roleDao.setSessionFactory(this.sessionFactory);

		this.auth.setUserDao(userDao);
		this.auth.setRoleDao(roleDao);

		this.sysm.setRoleDao(roleDao);
		this.sysm.setUserDao(userDao);

		// add users to table
		final User[] initialUsers = new User[] {
				new User().setName("wang").setPassword("123")
						.setBirthday(new Date()),
				new User().setName("huang").setPassword("111888")
						.setLastName("god") };

		new TransactionWrapper(this.sessionFactory.getCurrentSession()
				.getTransaction()) {
			@Override
			boolean go() {
				AuthenticationServiceTest.this.sysm.saveUsers(Arrays
						.asList(initialUsers));
				return true;
			};
		}.run();

	}

	@After
	public void teardown() {
		this.sessionFactory.close();

		this.server.shutdown();
	}

	@Test
	public void testGetUserByName() {
		assertTrue(new TransactionWrapper(this.sessionFactory
				.getCurrentSession().getTransaction()) {
			@Override
			boolean go() {
				List<User> users = AuthenticationServiceTest.this.auth
						.getUserByName("huang");
				for (User u : users)
					System.out.println(u);
				return users.size() == 1;
			}
		}.run());
	}

	abstract class TransactionWrapper {
		private final Transaction trans;
		private Throwable t = null;

		TransactionWrapper(Transaction trans) {
			this.trans = trans;
		}

		/**
		 * Override this
		 * 
		 * @return
		 */
		abstract boolean go();

		final boolean run() {
			boolean result = false;
			this.trans.begin();
			try {
				result = go();
			} catch (Throwable e) {
				this.t = e;
			} finally {
				if (this.t == null) {
					this.trans.commit();
				} else {
					this.trans.rollback();
				}
			}
			return result;
		}
	}

}
