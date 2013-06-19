package tk.blizz.ssh.model.impl;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tk.blizz.ssh.model.Function;

@Entity
@Table(name = "FUNCTION", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class FunctionImpl implements Function {
	private static final long serialVersionUID = 2013061901L;

	private Long id;
	private String name;
	private URL url;

	@Override
	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	@Override
	public Function setId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	@Column(unique = true, nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	@Override
	public Function setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public URL getUrl() {
		return this.url;
	}

	@Override
	public Function setUrl(URL url) {
		this.url = url;
		return this;
	}

	@Override
	public String toString() {
		return String.format("%d, %s, %s", this.id, this.name,
				this.url.toString());
	}

}
