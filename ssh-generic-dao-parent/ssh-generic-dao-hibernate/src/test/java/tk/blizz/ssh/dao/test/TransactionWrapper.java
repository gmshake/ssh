package tk.blizz.ssh.dao.test;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Transaction Wrapper
 * 
 * @author zlei.huang@gmail.com (2013-07-12)
 * 
 */
public class TransactionWrapper {
	private final SessionFactory sf;
	private final int timeout;

	/**
	 * with default timeout 5 seconds
	 * 
	 * @param sf
	 */
	public TransactionWrapper(SessionFactory sf) {
		this(sf, 5);
	}

	public TransactionWrapper(SessionFactory sf, int timeout) {
		if (sf == null)
			throw new NullPointerException("need session factory");

		this.sf = sf;
		this.timeout = timeout;
	}

	public boolean run(Block block) {
		boolean status = true;
		final Transaction transaction = this.sf.getCurrentSession()
				.getTransaction();
		transaction.setTimeout(this.timeout);

		transaction.begin();
		try {
			status = block.execute();
			transaction.commit();
		} catch (RuntimeException e) {
			// reverse the return value
			status = !status;
			try {
				transaction.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
		}
		return status;
	}

}
