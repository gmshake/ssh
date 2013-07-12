package tk.blizz.ssh.action;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tk.blizz.ssh.service.AuthenticationService;

import com.opensymphony.xwork2.ActionSupport;

public class HelloAction extends ActionSupport {
	private static final long serialVersionUID = 2013052701L;

	private static final Logger log = LoggerFactory
			.getLogger(HelloAction.class);

	private String username;
	private String password;

	public class HelloWorld {
		String hello;
		String[] world;

		public String getHello() {
			return this.hello;
		}

		public String[] getWorld() {
			return this.world;
		}
	}

	private HelloWorld helloWorld;

	@Override
	public void validate() {
		log.debug("validate username and password...");
		if (this.username == null)
			addFieldError("username", "username required");
		if (this.password == null)
			addFieldError("password", "password required");
	}

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
		addActionMessage("hi");
		return SUCCESS;
	}

	public String bad() {
		HelloWorld h = new HelloWorld();
		h.hello = this.username;
		h.world = new String[] { "wrong password", this.password };
		this.helloWorld = h;
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

	public HelloWorld getHelloWorld() {
		return this.helloWorld;
	}

	// setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
