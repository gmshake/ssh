package tk.blizz.ssh.service;

import java.util.List;

import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;

public interface AuthenticationService {
	List<User> getUserByName(String name);

	List<Role> getRoleByName(String name);

	boolean isValidUserInfo(String name, String pwd);

	boolean isValidUserInfo(String name, String hashPwd, String salt);
}
