package tk.blizz.ssh.dao;

import java.io.Serializable;

public interface SerializablePojo<T extends SerializablePojo<T, PK>, PK extends Serializable> extends
		Serializable {
	PK getId();

	T setId(PK id);
}
