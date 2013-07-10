package tk.blizz.ssh.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tk.blizz.ssh.model.User;
import tk.blizz.ssh.service.AuthenticationService;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private String username;
	private String password;

	@Override
	public String execute() {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getServletContext());
		AuthenticationService authenticationService = (AuthenticationService) ctx
				.getBean("authenticationService");

		User user = authenticationService.login(this.username, this.password);
		if (user != null)
			return SUCCESS;
		else
			return ERROR;
	}

	@Override
	public void validate() {
		if (this.username == null || this.username.isEmpty())
			addFieldError("username", "username required");
		if (this.password == null || this.password.isEmpty())
			addFieldError("password", "password required");

		addActionMessage("valid user");
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
