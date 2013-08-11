package tk.blizz.ssh.web.utils.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationFilter implements Filter {
	private static final Logger log = LoggerFactory
			.getLogger(AuthorizationFilter.class);

	private final AtomicLong id = new AtomicLong();

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.debug("init ...");
		this.id.set(0L);
		log.debug("init done");
	}

	@Override
	public void destroy() {
		log.debug("destroy ...");
		log.debug("destroy done");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final long id = this.id.getAndIncrement();
		log.debug("do filter {} ...", id);
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			if (!req.isRequestedSessionIdValid())
				throw new AuthorizationException("session is not valid");
			chain.doFilter(request, response);
		} finally {
			log.debug("filter {} done", id);
		}

	}

}
