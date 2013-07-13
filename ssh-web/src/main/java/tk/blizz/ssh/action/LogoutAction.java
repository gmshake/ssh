package tk.blizz.ssh.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport {
	@Override
	public String execute() {
		HttpSession session = ServletActionContext.getRequest().getSession(
				false);

		String msg = "log out successfully";
		if (session != null) {
			String user = (String) session.getAttribute("username");
			if (user != null)
				msg = user + " " + msg;

			session.invalidate();
		}

		addActionMessage(msg);
		return SUCCESS;
	}
}
