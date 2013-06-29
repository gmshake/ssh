package tk.blizz.ssh.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tk.blizz.ssh.service.AuthenticationService;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
	private static final long serialVersionUID = 2013052701L;

	private String username;
	private String password;

	class HelloWorld {
		String hello;
		String[] world;
	}

	private HelloWorld helloWorld;

	@Override
	public String execute() {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getServletContext());
		AuthenticationService authenticationService = (AuthenticationService) ctx
				.getBean("authenticationService");

		if (authenticationService.isValidUserInfo(this.username, this.password))

			return hello();
		else
			return bad();
	}

	public String hello() {
		HelloWorld h = new HelloWorld();
		h.hello = "Hello";
		h.world = new String[] { "World1", "World2", "World3", "wOrld4" };

		this.helloWorld = h;

		return SUCCESS;
	}

	public String bad() {
		HelloWorld h = new HelloWorld();
		h.hello = this.username;
		h.world = new String[] { "wrong password", this.password };
		return ERROR;
	}

	public String jsonHello() {
		return execute();
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

	// setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
