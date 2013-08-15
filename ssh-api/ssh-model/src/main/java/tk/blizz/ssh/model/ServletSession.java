package tk.blizz.ssh.model;

import java.io.Serializable;
import java.util.Date;

/**
 * http servlet session
 * 
 * @author zlei.huang@gmail.com 2013-08-10
 * 
 */
public interface ServletSession extends Serializable {
	Long getId();

	String getSessionId();

	String getContextPath();

	Boolean isValid();

	Date getCreateTime();

	Date getAccessTime();

	Integer getMaxInactiveInterval();
}
