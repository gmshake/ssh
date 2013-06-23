package tk.blizz.ssh.dao.impl;

import java.util.List;

import tk.blizz.ssh.dao.RoleDAO;
import tk.blizz.ssh.model.Role;

public class RoleDAOImpl extends GenericHibernateDAO<Role, Role, Long>
		implements RoleDAO {

	@Override
	public List<Role> findByRoleName(String name) {
		Role example = new Role().setName(name);
		return findByExample(example);
	}

}
