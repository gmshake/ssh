package tk.blizz.ssh.web.utils.filter;

import javax.servlet.ServletException;

/**
 * 
 * @author zlei
 * 
 */
public class AuthenticationException extends ServletException {
	private static final long serialVersionUID = -5374445108970948648L;

	public AuthenticationException() {
	}

	public AuthenticationException(String arg0) {
		super(arg0);
	}

	public AuthenticationException(Throwable arg0) {
		super(arg0);
	}

	public AuthenticationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
