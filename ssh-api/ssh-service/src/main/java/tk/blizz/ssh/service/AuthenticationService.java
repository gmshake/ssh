package tk.blizz.ssh.service;

import java.util.List;

import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;

public interface AuthenticationService {
	List<User> getUserByName(String name,
			ServiceCallBack<List<? extends User>>... callbacks);

	List<Role> getRoleByName(String name,
			ServiceCallBack<List<? extends Role>>... callbacks);

	boolean isValidUserInfo(String name, String pwd);

	boolean isValidUserInfo(String name, String hashPwd, String salt);

}
