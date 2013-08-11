package tk.blizz.ssh.web.utils.logging;

import javax.servlet.http.HttpServletRequest;

public interface HttpServletRequestLogger {
	void log(HttpServletRequest request);
}
