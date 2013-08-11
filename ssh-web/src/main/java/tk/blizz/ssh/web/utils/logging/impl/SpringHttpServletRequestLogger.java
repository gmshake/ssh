package tk.blizz.ssh.web.utils.logging.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.blizz.ssh.dao.ServletSessionDao;
import tk.blizz.ssh.web.utils.logging.HttpServletRequestLogger;

public class SpringHttpServletRequestLogger implements HttpServletRequestLogger {
	private static final Logger log = LoggerFactory
			.getLogger(SpringHttpServletRequestLogger.class);

	private ServletSessionDao sessionDao;

	@Override
	public void log(HttpServletRequest request) {
		log.debug("request URI: {}", request.getRequestURI().toString());
		log.debug("request referrer: {}", request.getHeader("referer"));
		log.debug("request query string: {}", request.getQueryString());
		log.debug("request requested sessionid: {}",
				request.getRequestedSessionId());
		log.debug("request method: {}", request.getMethod());
		log.debug("request pathinfo: {}", request.getPathInfo());
		log.debug("request has session: {}",
				request.getSession(false) != null ? true : false);

	}

}
