package tk.blizz.ssh.dao.impl;

import java.util.List;

import tk.blizz.ssh.dao.UserDAO;
import tk.blizz.ssh.model.User;

public class UserDAOImpl extends GenericHibernateDAO<User, User, Long>
		implements UserDAO {

	@Override
	public List<User> findByUserName(String name) {
		User example = new User().setName(name);
		return findByExample(example);
	}

}
