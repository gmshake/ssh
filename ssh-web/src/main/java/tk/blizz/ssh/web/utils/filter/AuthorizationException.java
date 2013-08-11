package tk.blizz.ssh.web.utils.filter;

import javax.servlet.ServletException;

/**
 * 
 * @author zlei
 * 
 */
public class AuthorizationException extends ServletException {
	private static final long serialVersionUID = 8666440243534924766L;

	public AuthorizationException() {
	}

	public AuthorizationException(String arg0) {
		super(arg0);
	}

	public AuthorizationException(Throwable arg0) {
		super(arg0);
	}

	public AuthorizationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
