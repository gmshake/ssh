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

/**
 * Filter requests, block requests that do NOT have session
 * 
 * @author zlei
 * 
 */
public class SessionFilter implements Filter {
	private static final Logger log = LoggerFactory
			.getLogger(SessionFilter.class);

	private final AtomicLong id = new AtomicLong();

	private String welcome;

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.debug("init ...");
		String welcome = config.getInitParameter("welcome");
		if (welcome == null) {
			throw new ServletException("need parameter welcome");
		}
		this.welcome = welcome;

		this.id.set(0L);

		log.debug("init done");
	}

	@Override
	public void destroy() {
		log.debug("destroy ...");
		this.welcome = null;
		log.debug("destroy done");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final long id = this.id.getAndIncrement();
		log.debug("do filter {}...", id);
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			final HttpServletRequest req = (HttpServletRequest) request;
			final String contextPath = req.getServletContext().getContextPath();
			final String uri = req.getRequestURI().substring(
					contextPath.length());
			if (req.isRequestedSessionIdValid() || uri.equals(this.welcome)) {
				chain.doFilter(request, response);
			} else {
				// TODO fix home
				log.debug("request sessionId {} is invalid, redirect to {}",
						req.getRequestedSessionId(), contextPath + this.welcome);
				final HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect(contextPath + this.welcome);
			}

		} else {
			log.debug(
					"reqest is not an instance of {} or reqest is not an instance of {}, pass through...",
					HttpServletRequest.class.getName(),
					HttpServletResponse.class.getName());
			chain.doFilter(request, response);
		}
		log.debug("filter {} done", id);
	}

}
