package tk.blizz.ssh.web.utils.logging.impl;

import javax.servlet.http.HttpServletRequest;

import tk.blizz.ssh.web.utils.logging.HttpServletRequestLogger;

public class NopHttpServletRequestLogger implements HttpServletRequestLogger {

	@Override
	public void log(HttpServletRequest request) {
		// do nothing
	}

}
