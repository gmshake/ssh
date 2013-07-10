package tk.blizz.ssh.interceptor;

import java.util.Map;

import tk.blizz.ssh.action.LoginAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthenticationInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();

		Integer userId = (Integer) session.get("userId");

		if (userId != null) {
			return invocation.invoke();
		}

		Object action = invocation.getAction();

		// sb: if the action doesn't require sign-in, then let it through.
		if (!(action instanceof LoginRequired)) {
			return invocation.invoke();
		}

		// sb: if this request does require login and the current action is
		// not the login action, then redirect the user
		if (!(action instanceof LoginAction)) {

			return "loginRedirect";
		}

		// sb: they either requested the login page or are submitting their
		// login now, let it through
		return invocation.invoke();

	}

}
