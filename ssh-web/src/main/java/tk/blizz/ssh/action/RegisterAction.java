package tk.blizz.ssh.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tk.blizz.ssh.service.AuthenticationService;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {
	private UserBean user = new UserBean();
	private String confirmPassword;

	@Override
	public String execute() {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getServletContext());
		AuthenticationService authenticationService = (AuthenticationService) ctx
				.getBean("authenticationService");

		if (authenticationService.register(user.username, user.password))
			return SUCCESS;
		else
			return ERROR;
	}

	@Override
	public void validate() {
		if (this.user.username == null || this.user.username.isEmpty())
			addFieldError("user.username", "username required");
		if (this.user.password == null || this.user.password.isEmpty())
			addFieldError("user.password", "password required");
		if (this.user.password != null
				&& !this.user.password.equals(this.confirmPassword))
			addFieldError("confirmPassword", "password not same");
	}

	// setters
	public void setUser(UserBean user) {
		this.user = user;
	}

	public UserBean getUser() {
		return this.user;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public class UserBean {
		private String username;
		private String password;

		public String getUsername() {
			return this.username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
