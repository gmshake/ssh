package tk.blizz.ssh.dao.test;

import java.io.Serializable;

public interface User extends Serializable {

	Long getId();

	User setId(Long id);

	String getName();

	User setName(String name);

	String getCName();

	User setCName(String cname);
}
