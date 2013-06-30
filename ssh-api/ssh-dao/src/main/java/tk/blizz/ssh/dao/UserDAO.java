package tk.blizz.ssh.dao;

import tk.blizz.ssh.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
	/**
	 * find any users by user's name
	 * 
	 * @param name
	 * @return
	 */
	User findByUserName(String name);

}
