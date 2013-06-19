package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tk.blizz.ssh.dao.impl.FunctionDaoImpl;
import tk.blizz.ssh.model.Function;
import tk.blizz.ssh.model.impl.FunctionImpl;

/**
 * Tests for {@link tk.blizz.ssh.dao.impl.UserDAOImpl}.
 * 
 * @author zlei.huang@gmail.com (Huang Zhenlei)
 * 
 */
public class TestFunctionDao {

	org.hsqldb.server.Server server;
	private SessionFactory sessionFactory;
	private FunctionDaoImpl dao;

	private Transaction trans;

	private boolean hasError;

	@Before
	public void setup() {
		this.server = new org.hsqldb.server.Server();
		this.server.setDatabaseName(0, "memdb");
		this.server.setDatabasePath(0, "mem:memdb");
		this.server.start();

		this.sessionFactory = new Configuration().configure()
				.addAnnotatedClass(FunctionImpl.class).buildSessionFactory();

		this.dao = new FunctionDaoImpl();
		this.dao.setSessionFactory(this.sessionFactory);

		this.trans = this.sessionFactory.getCurrentSession().beginTransaction();
	}

	@After
	public void teardown() {
		if (this.hasError)
			this.trans.rollback();
		else
			this.trans.commit();
		this.dao.setSessionFactory(null);
		this.sessionFactory.close();

		this.server.shutdown();
	}

	@Test
	public void testSave() throws Exception {
		try {
			Function u = new FunctionImpl();
			u.setName("Hello");
			u.setUrl(new URL("http://www.blizz.tk/"));
			Long id = this.dao.save(u);

			assertEquals(id, u.getId());

			Function f = this.dao.findById(id);

			assertTrue(f != null);

			System.out.println("-----------------------------------");
			System.out.println(f);
			System.out.println("-----------------------------------");

			assertTrue(u.getName().equals(f.getName()));
			assertTrue(u.getUrl().equals(f.getUrl()));

		} catch (Exception e) {
			this.hasError = true;
			throw e;
		}
	}

}
