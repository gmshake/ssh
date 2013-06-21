package tk.blizz.ssh.dao;

public class GenericDaoException extends RuntimeException {

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
