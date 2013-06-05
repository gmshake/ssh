package tk.blizz.ssh.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 2013052501L;

	private Long id;
	private String name;
	private String lastName;
	private String password;
	private Date birthday;

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public User setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public User setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public User setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}

}
