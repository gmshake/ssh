package tk.blizz.ssh.service;

import java.util.List;

import tk.blizz.ssh.model.Role;
import tk.blizz.ssh.model.User;

public interface SystemManageService {
	void saveUsers(List<User> users);

	void saveRoles(List<Role> roles);
}
