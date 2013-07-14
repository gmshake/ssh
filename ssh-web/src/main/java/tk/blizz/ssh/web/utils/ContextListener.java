package tk.blizz.ssh.web.utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextListener implements ServletContextListener {
	private static final Logger log = LoggerFactory
			.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.debug("context initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.debug("context destroyed");
	}

	@PostConstruct
	public void postConstruct() {
		log.info("context is constructed...");
	}

	@PreDestroy
	public void doDestroy() {
		log.info("context is to be destroyed...");
	}

}
