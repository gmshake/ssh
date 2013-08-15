package tk.blizz.ssh.dao.impl;

import tk.blizz.ssh.dao.ServletSessionDao;
import tk.blizz.ssh.model.ServletSession;
import tk.blizz.ssh.model.impl.ServletSessionImpl;

public class ServletSessionDaoImpl extends
		GenericHibernateDAO<ServletSession, ServletSessionImpl, Long> implements
		ServletSessionDao {

}
