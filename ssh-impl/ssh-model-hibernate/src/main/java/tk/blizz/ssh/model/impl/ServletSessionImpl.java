package tk.blizz.ssh.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tk.blizz.ssh.model.ServletSession;

@Entity
@Table(name = "SERVLET_SESSION", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"sessionId", "contextPath" }) })
public class ServletSessionImpl implements ServletSession {
	private static final long serialVersionUID = -1120826344082264967L;
	private Long id;
	private String sessionId;
	private String contextPath;
	private Boolean valid;
	private Date createTime;
	private Date accessTime;
	private Integer maxInactiveInterval;

	public ServletSessionImpl() {
	}

	public ServletSessionImpl(ServletSession s) {
		this.id = s.getId();
		this.sessionId = s.getSessionId();
		this.contextPath = s.getContextPath();
		this.valid = s.isValid();
		this.createTime = s.getCreateTime();
		this.accessTime = s.getAccessTime();
		this.maxInactiveInterval = s.getMaxInactiveInterval();
	}

	@Override
	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	@Override
	@Column(nullable = false, length = 100)
	public String getSessionId() {
		return this.sessionId;
	}

	@Override
	@Column(nullable = false, length = 100)
	public String getContextPath() {
		return this.contextPath;
	}

	@Override
	@Column(nullable = false)
	public Boolean isValid() {
		return this.valid;
	}

	@Override
	@Column(nullable = false)
	public Date getCreateTime() {
		return this.createTime;
	}

	@Override
	@Column(nullable = false)
	public Date getAccessTime() {
		return this.accessTime;
	}

	@Override
	@Column(nullable = false)
	public Integer getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}

	public ServletSessionImpl setId(Long id) {
		this.id = id;
		return this;
	}

	public ServletSessionImpl setSessionId(String sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	public ServletSessionImpl setContextPath(String contextPath) {
		this.contextPath = contextPath;
		return this;
	}

	public ServletSessionImpl setValid(Boolean valid) {
		this.valid = valid;
		return this;
	}

	public ServletSessionImpl setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public ServletSessionImpl setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
		return this;
	}

	public ServletSessionImpl setMaxInactiveInterval(Integer maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
		return this;
	}

}
