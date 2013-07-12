package tk.blizz.ssh.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.blizz.ssh.action.LoginAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthenticationInterceptor extends AbstractInterceptor {
	private static final Logger log = LoggerFactory
			.getLogger(AuthenticationInterceptor.class);

	private static final String USERID = "userId";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String result = null;
		if (!isAllowed(request, invocation.getAction())) {
			result = handleRedirect(invocation, response);
		} else {
			result = invocation.invoke();
		}
		return result;
	}

	/**
	 * Determines if the request should be allowed for the action
	 * 
	 * @param request
	 *            The request
	 * @param action
	 *            The action object
	 * @return True if allowed, false otherwise
	 */
	protected boolean isAllowed(HttpServletRequest request, Object action) {
		HttpSession session = request.getSession(true);
		synchronized (session) {
			if (session.isNew()) {
				log.debug("new session: {?}", session.getId());
			}
		}

		Map<String, Object> actionSession = ActionContext.getContext()
				.getSession();

		// sb: if the action doesn't require sign-in, then let it through.
		if (!(action instanceof LoginRequired)) {
			return true;
		}

		Integer userId = (Integer) session.getAttribute(USERID);

		if (userId != null) {
			log.debug("userid in session: {?}", userId);
			return true;
		}

		// sb: if this request does require login and the current action is
		// not the login action, then redirect the user
		if (!(action instanceof LoginAction)) {
			return false;
		}

		// sb: they either requested the login page or are submitting their
		// login now, let it through
		return false;
	}

	/**
	 * Handles a rejection by sending a 403 HTTP error
	 * 
	 * @param invocation
	 *            The invocation
	 * @return The result code
	 * @throws Exception
	 */
	protected String handleRejection(ActionInvocation invocation,
			HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	protected String handleRedirect(ActionInvocation invocation,
			HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return "loginRedirect";
	}

}
