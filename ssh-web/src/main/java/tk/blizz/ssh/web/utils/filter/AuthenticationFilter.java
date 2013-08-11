package tk.blizz.ssh.web.utils.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.regex.Pattern;

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

public class AuthenticationFilter implements Filter {
	private static final Logger log = LoggerFactory
			.getLogger(AuthenticationFilter.class);

	private final AtomicLong id = new AtomicLong();

	private final ArrayList<Pattern> excludedPatterns = new ArrayList<Pattern>();

	// lock for excludedPatterns
	private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.debug("init ...");
		this.id.set(0L);

		final WriteLock wlock = this.rwlock.writeLock();
		wlock.lock();
		try {
			this.excludedPatterns.clear();
			String pattern = config.getInitParameter("exclude");
			if (pattern != null && !pattern.isEmpty()) {
				this.excludedPatterns
						.addAll(buildExcludedPatternsList(pattern));
			}
		} finally {
			wlock.unlock();
		}

		log.debug("init done");
	}

	@Override
	public void destroy() {
		log.debug("destroy ...");
		final WriteLock wlock = this.rwlock.writeLock();
		wlock.lock();
		try {
			this.excludedPatterns.clear();
		} finally {
			wlock.unlock();
		}

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

			if (!matchExcludePatterns(getUri(req))
					&& !req.isRequestedSessionIdValid())
				throw new AuthenticationException("need authenticate");
			chain.doFilter(request, response);
		} finally {
			log.debug("filter {} done", id);
		}

	}

	private String getUri(HttpServletRequest request) {
		return request.getRequestURI().substring(
				request.getContextPath().length());
	}

	private boolean matchExcludePatterns(String url) {
		final ReadLock rlock = this.rwlock.readLock();
		rlock.lock();
		try {
			for (Pattern p : this.excludedPatterns) {
				if (p.matcher(url).matches())
					return true;
			}
		} finally {
			rlock.unlock();
		}
		return false;
	}

	private List<Pattern> buildExcludedPatternsList(String patterns) {
		List<Pattern> list = new ArrayList<Pattern>();
		String[] tokens = patterns.split("[,;]");
		for (String token : tokens) {
			list.add(Pattern.compile(token.trim()));
		}
		return Collections.unmodifiableList(list);
	}

}
