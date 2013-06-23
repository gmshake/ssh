package tk.blizz.ssh.dao.impl;

import tk.blizz.ssh.dao.FunctionDao;
import tk.blizz.ssh.model.Function;
import tk.blizz.ssh.model.impl.FunctionImpl;

public class FunctionDaoImpl extends
		GenericHibernateDAO<Function, FunctionImpl, Long> implements
		FunctionDao {

	@Override
	public FunctionImpl findByName(String name) {
		return findUniqueByExample(new FunctionImpl().setName(name));
	}

}
