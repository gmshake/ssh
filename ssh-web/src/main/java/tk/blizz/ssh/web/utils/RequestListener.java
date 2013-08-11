package tk.blizz.ssh.web.utils;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.blizz.ssh.web.utils.logging.LogFactory;
import tk.blizz.ssh.web.utils.logging.impl.SpringLogFactory;

public class RequestListener implements ServletRequestListener {
	private static final Logger log = LoggerFactory
			.getLogger(RequestListener.class);

	private final AtomicLong requestCount = new AtomicLong();

	private final LogFactory loggerFactory = new SpringLogFactory();

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		// final ServletContext context = event.getServletContext();
		final ServletRequest request = event.getServletRequest();
		final long count = this.requestCount.incrementAndGet();

		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			log.debug("request count: {}", count);
			loggerFactory.getHttpServletRequestLogger().log(req);
			log.debug("wait a second...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			log.debug("ok");
		}

	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		log.debug("request destroyed");
	}

}
