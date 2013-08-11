package tk.blizz.ssh.web.utils.logging.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import tk.blizz.ssh.web.utils.logging.HttpServletRequestLogger;
import tk.blizz.ssh.web.utils.logging.LogFactory;

public class SpringLogFactory implements LogFactory {
	private static final Logger log = LoggerFactory
			.getLogger(SpringLogFactory.class);

	@Override
	public HttpServletRequestLogger getHttpServletRequestLogger() {
		final ApplicationContext context = ContextLoader
				.getCurrentWebApplicationContext();
		if (context == null) {
			log.warn("no spring context found, use NopHttpServletRequestLogger");
			return new NopHttpServletRequestLogger();
		} else {
			final HttpServletRequestLogger httpServletRequestLogger = context
					.getBean("httpServletRequestLogger",
							HttpServletRequestLogger.class);
			if (httpServletRequestLogger == null) {
				log.warn("no HttpServletRequestLogger found in spring context");
				return new NopHttpServletRequestLogger();
			} else {
				log.debug("HttpServletRequestLogger found");
				return httpServletRequestLogger;
			}

		}
	}

}
