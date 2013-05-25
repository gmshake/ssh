package tk.blizz.ssh.service.impl;

import java.util.List;

import tk.blizz.ssh.dao.RoleDAO;
import tk.blizz.ssh.dao.UserDAO;
import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;
import tk.blizz.ssh.service.AuthenticationService;
import tk.blizz.utils.HexToString;

public class AuthenticationServiceImpl implements AuthenticationService {
	private UserDAO userDao;
	private RoleDAO roleDao;

	public List<User> getUserByName(String name) {
		return this.userDao.findByUserName(name);
	}

	public List<Role> getRoleByName(String name) {
		return this.roleDao.findByRoleName(name);
	}

	public boolean isValidUserInfo(String name, String pwd) {
		User user = new User().setName(name).setPassword(pwd);
		List<User> l = this.userDao.findByExample(user);
		return l.size() > 0;
	}

	public boolean isValidUserInfo(String name, String hashPwd, String salt) {
		User user = new User().setName(name);
		List<User> l = this.userDao.findByExample(user);
		for (User u : l) {
			if (HexToString.hashUserNamePwdSalt(u.getName(), u.getPassword(),
					salt).equalsIgnoreCase(hashPwd))
				return true;
		}
		return false;
	}

	// setters, injected by spring
	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param roleDao
	 *            the roleDao to set
	 */
	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

}
