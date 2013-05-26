package tk.blizz.ssh.action;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
	private static final long serialVersionUID = 2013052701L;

	class HelloWorld {
		String hello;
		String[] world;
	}

	private HelloWorld helloWorld;

	@Override
	public String execute() {
		return hello();
	}

	public String hello() {
		HelloWorld h = new HelloWorld();
		h.hello = "Hello";
		h.world = new String[] { "World1", "World2", "World3", "wOrld4" };

		this.helloWorld = h;

		return SUCCESS;
	}

	public String jsonHello() {
		return hello();
	}

	// json getters
	/**
	 * json getters
	 * 
	 * @return
	 */
	public String getHello() {
		return this.helloWorld.hello;
	}

	public String[] getWorld() {
		return this.helloWorld.world;
	}
}
