package tk.blizz.ssh.service.impl;

import java.util.List;

import tk.blizz.ssh.dao.RoleDAO;
import tk.blizz.ssh.dao.UserDAO;
import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;
import tk.blizz.ssh.service.SystemManageService;

public class SystemManageServiceImpl implements SystemManageService {
	private UserDAO userDao;
	private RoleDAO roleDao;

	@Override
	public void saveUsers(List<User> users) {
		for (User user : users) {
			this.userDao.save(user);
		}
	}

	@Override
	public void saveRoles(List<Role> roles) {
		for (Role role : roles) {
			this.roleDao.save(role);
		}
	}

	// setters
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

}
