package tk.blizz.ssh.dao.test;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "USER")
public class UserImpl implements User {
	private Long id;
	private String name;
	private String cname;

	private Date created;
	private Date updated;

	private Integer version;

	public UserImpl() {
	}

	/**
	 * copy constructor
	 * 
	 * @param user
	 */
	public UserImpl(User u) {
		this.id = u.getId();
		this.name = u.getName();
		this.cname = u.getCName();
	}

	@Override
	@Id
	@Column(name = "ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	@Override
	public User setId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	@Column(name = "NAME", length = 32, unique = true, updatable = false)
	public String getName() {
		return this.name;
	}

	@Override
	public User setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	@Column(name = "CNAME", length = 64)
	public String getCName() {
		return this.cname;
	}

	@Override
	public User setCName(String cname) {
		this.cname = cname;
		return this;
	}

	@Version
	@Column(name = "OPTLOCK", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(nullable = true, updatable = false)
	public Date getCreateTime() {
		return this.created;
	}

	@PrePersist
	public void setCreateTime(Date date) {
		this.updated = this.created = new Date();
	}

	@Column(nullable = true)
	public Date getUpdateTime() {
		return this.updated;
	}

	@PreUpdate
	public void setUpdateTime(Date date) {
		this.updated = new Date();
	}

	@Override
	public String toString() {
		return String.format("%s: {%d, %s, %s, %s, %s, %d}", this.getClass()
				.getName(), this.id, this.name, this.cname, this.created,
				this.updated, this.version);
	}

}
