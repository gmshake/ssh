package tk.blizz.ssh.dao.test;

import org.hsqldb.server.Server;
import org.junit.After;
import org.junit.Before;

/**
 * Base Test for tests
 * 
 * @author zlei.huang@gmail.com (Huang Zhenlei)
 * 
 */
public abstract class TestBase {
	protected final Server server = new Server();

	@Before
	public void setup() {
		// this.server.setDatabaseName(0, "memdb");
		// this.server.setDatabasePath(0, "mem:memdb");
		// this.server.start();
	}

	@After
	public void teardown() {
		// this.server.shutdown();
	}

	protected Server getServer() {
		return this.server;
	}
}
