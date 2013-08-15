package tk.blizz.ssh.web.utils.logging.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.blizz.ssh.web.utils.logging.HttpServletRequestLogger;

public class SpringHttpServletRequestLogger implements HttpServletRequestLogger {
	private static final Logger log = LoggerFactory
			.getLogger(SpringHttpServletRequestLogger.class);

	// private ServletSessionDao sessionDao;

	@Override
	public void log(HttpServletRequest request) {
		log.debug("request URI: {}", request.getRequestURI());
		log.debug("request referrer: {}", request.getHeader("referer"));
		log.debug("request query string: {}", request.getQueryString());
		log.debug("request requested sessionid: {}",
				request.getRequestedSessionId());
		log.debug("request method: {}", request.getMethod());
		log.debug("request pathinfo: {}", request.getPathInfo());
		log.debug("request has session: {}",
				request.getSession(false) != null ? true : false);
		final Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			log.debug("request cookies: null");
		} else {
			final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>(
					cookies.length);
			for (Cookie c : cookies) {
				list.add(buildCookieMap(c));
			}
			log.debug("request cookies: {}", list);
			// clean up, help GC
			for (Map<String, String> m : list) {
				m.clear();
			}
			list.clear();
		}
	}

	private Map<String, String> buildCookieMap(final Cookie cookie) {
		final HashMap<String, String> map = new HashMap<String, String>(11);
		map.put("name", cookie.getName());
		map.put("value", cookie.getValue());
		map.put("domain", cookie.getDomain());
		map.put("path", cookie.getPath());
		map.put("maxAge", String.valueOf(cookie.getMaxAge()));
		map.put("secure", String.valueOf(cookie.getSecure()));
		map.put("version", String.valueOf(cookie.getVersion()));
		map.put("httpOnly", String.valueOf(cookie.isHttpOnly()));
		map.put("comment", cookie.getComment());
		return map;
	}

}
