package tk.blizz.ssh.dao.test;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import tk.blizz.ssh.dao.impl.GenericHibernateDAO;

/**
 * wrapper for transaction
 * 
 * @author zlei.huang@gmail.com (2013-06-20)
 * 
 */
public abstract class DaoWrapper<T extends GenericHibernateDAO<?, ?>> {
	protected final T dao;

	public DaoWrapper(T dao) {
		this.dao = dao;
	}

	/**
	 * Override this
	 * 
	 * @return
	 */
	protected abstract boolean go();

	public final boolean run() {
		boolean result = false;
		Throwable t = null;
		final Transaction trans = this.dao.getSessionFactory()
				.getCurrentSession().beginTransaction();
		try {
			result = go();
		} catch (Exception e) {
			t = e;
			e.printStackTrace();
		} finally {
			try {
				if (t == null) {
					trans.commit();
				} else {
					trans.rollback();
				}
			} catch (HibernateException e) {
				result = false;
				e.printStackTrace();
			}
		}
		return result;
	}
}
