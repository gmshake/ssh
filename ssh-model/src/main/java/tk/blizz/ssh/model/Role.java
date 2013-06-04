package tk.blizz.ssh.model;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
	private static final long serialVersionUID = 2013052501L;

	private Long id;
	private String name;
	private Date createTime;
	private String description;

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
	public Role setId(Long id) {
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
	 * @return
	 */
	public Role setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public Role setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public Role setDescription(String description) {
		this.description = description;
		return this;
	}
}
