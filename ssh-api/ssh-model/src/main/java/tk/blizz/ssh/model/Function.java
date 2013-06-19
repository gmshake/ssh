package tk.blizz.ssh.model;

import java.io.Serializable;
import java.net.URL;

public interface Function extends Serializable {
	Long getId();

	Function setId(Long id);

	String getName();

	Function setName(String name);

	URL getUrl();

	Function setUrl(URL url);

}
