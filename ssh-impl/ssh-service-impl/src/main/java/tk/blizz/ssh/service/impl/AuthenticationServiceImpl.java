package tk.blizz.ssh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.blizz.ssh.dao.RoleDAO;
import tk.blizz.ssh.dao.UserDAO;
import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;
import tk.blizz.ssh.service.AuthenticationService;
import tk.blizz.ssh.service.ServiceCallBack;
import tk.blizz.utils.HexToString;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AuthenticationServiceImpl implements AuthenticationService {
	private UserDAO userDao;
	private RoleDAO roleDao;

	@Override
	public List<User> getUserByName(String name,
			ServiceCallBack<List<? extends User>>... callbacks) {

		User user = this.userDao.findByUserName(name);

		List<User> list = new ArrayList<User>(1);
		list.add(user);

		for (ServiceCallBack<List<? extends User>> callback : callbacks) {
			callback.call(list);
		}
		return list;
	}

	@Override
	public List<Role> getRoleByName(String name,
			ServiceCallBack<List<? extends Role>>... callbacks) {
		List<Role> roles = this.roleDao.findByRoleName(name);
		for (ServiceCallBack<List<? extends Role>> callback : callbacks) {
			callback.call(roles);
		}
		return roles;
	}

	@Override
	public boolean isValidUserInfo(String name, String pwd) {
		User user = new User().setName(name).setPassword(pwd);
		List<? extends User> l = this.userDao.findByExample(user);
		return l.size() > 0;
	}

	@Override
	public boolean isValidUserInfo(String name, String hashPwd, String salt) {
		User user = new User().setName(name);
		List<? extends User> l = this.userDao.findByExample(user);
		for (User u : l) {
			if (HexToString.hashUserNamePwdSalt(u.getName(), u.getPassword(),
					salt).equalsIgnoreCase(hashPwd))
				return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean register(String name, String pwd) {
		User user = this.userDao.findByUserName(name);
		if (user != null)
			return false;

		user = new User().setName(name).setPassword(pwd);
		this.userDao.save(user);
		return true;
	}

	@Override
	public User login(String username, String password) {
		User user = this.userDao.findByUserName(username);
		if (user == null)
			return null;
		if (user.getPassword() != null && user.getPassword().equals(password))
			return user;
		else
			return null;
	}

	@Override
	public User getUserById(Long id) {
		User user = this.userDao.findById(id);
		return user;
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
