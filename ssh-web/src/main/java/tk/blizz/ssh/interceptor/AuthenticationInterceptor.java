package tk.blizz.ssh.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import tk.blizz.ssh.action.LoginAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * Interceptor check if user require login
 * 
 * @author zlei.huang@gmail.com
 * 
 */
public class AuthenticationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 4426192299112482045L;

	private static final Logger LOG = LoggerFactory
			.getLogger(AuthenticationInterceptor.class);

	private static final String USERID = "userid";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Check Authentication");
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		if (!isAllowed(request, invocation))
			return handleRedirect(invocation, response);

		return invocation.invoke();
	}

	protected boolean isLoginRequiedAction(Object action) {
		// sb: if the action doesn't require sign-in, then let it through.
		LoginRequired r = action.getClass().getAnnotation(LoginRequired.class);
		if (r != null && !r.loginRequired()) {
			return false;
		}

		// sb: if this request does require login and the current action is
		// not the login action, then redirect the user
		if (action instanceof LoginAction) {
			return false;
		}

		// if in white list
		// return false
		return true;
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
	protected boolean isAllowed(HttpServletRequest request,
			ActionInvocation invocation) {
		// HttpSession httpSession = request.getSession(true);
		// synchronized (httpSession) {
		// if (httpSession.isNew()) {
		// if (LOG.isDebugEnabled())
		// LOG.debug("new session: #0", httpSession.getId());
		// }
		// }

		if (!isLoginRequiedAction(invocation.getAction()))
			return true;

		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();

		Long userId = (Long) session.get(USERID);

		if (userId != null) {
			if (LOG.isDebugEnabled())
				LOG.debug("userid in session: #0", userId);
			return true;
		}

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

	/**
	 * Handles a redirect to login page
	 * 
	 * @param invocation
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected String handleRedirect(ActionInvocation invocation,
			HttpServletResponse response) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Redirect to login page");
		}
		// response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return "login";
	}

}
