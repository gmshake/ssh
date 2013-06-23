package tk.blizz.ssh.dao;

public class GenericDaoException extends RuntimeException {
	private static final long serialVersionUID = 2013062301L;

	public GenericDaoException() {
	}

	public GenericDaoException(String message) {
		super(message);
	}

	public GenericDaoException(Throwable cause) {
		super(cause);
	}

	public GenericDaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
