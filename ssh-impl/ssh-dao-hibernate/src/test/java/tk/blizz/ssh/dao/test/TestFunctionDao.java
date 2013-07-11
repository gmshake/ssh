package tk.blizz.ssh.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tk.blizz.ssh.dao.impl.FunctionDaoImpl;
import tk.blizz.ssh.model.Function;
import tk.blizz.ssh.model.impl.FunctionImpl;

/**
 * Tests for {@link tk.blizz.ssh.dao.impl.FunctionDaoImpl}.
 * 
 * @author zlei.huang@gmail.com (Huang Zhenlei)
 * 
 */
public class TestFunctionDao extends TestBase {

	private final FunctionDaoImpl dao = new FunctionDaoImpl();

	private Transaction trans;

	private boolean hasError;

	@Override
	@Before
	public void setup() {
		final Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(FunctionImpl.class);
		final ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());

		final SessionFactory sf = configuration
				.buildSessionFactory(serviceRegistryBuilder
						.buildServiceRegistry());

		this.dao.setSessionFactory(sf);

		this.trans = sf.getCurrentSession().beginTransaction();
	}

	@Override
	@After
	public void teardown() {
		if (this.hasError)
			this.trans.rollback();
		else
			this.trans.commit();

		final SessionFactory sf = this.dao.getSessionFactory();
		sf.close();
	}

	@Test
	public void testSave() throws Exception {
		try {
			Function u = new FunctionImpl();
			u.setName("Hello");
			u.setUrl(new URL("http://www.blizz.tk/"));

			Function n = new FunctionImpl(u).setName("World");
			Long id = this.dao.save(u);
			this.dao.save(n);

			assertEquals(id, u.getId());

			Function f = this.dao.findById(id);

			assertTrue(f != null);

			System.out.println("-----------------------------------");
			System.out.println(f);
			System.out.println("-----------------------------------");

			assertTrue(u.getName().equals(f.getName()));
			assertTrue(u.getUrl().equals(f.getUrl()));

			List<FunctionImpl> fs = this.dao.findAll();
			System.out.println("-----------------------------------");
			for (Function i : fs) {
				System.out.println(i);
			}
			System.out.println("-----------------------------------");

		} catch (Exception e) {
			this.hasError = true;
			throw e;
		}
	}

}
