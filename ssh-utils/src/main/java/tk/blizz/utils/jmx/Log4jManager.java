package tk.blizz.utils.jmx;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.jmx.HierarchyDynamicMBean;

/**
 * Manage Log4j
 * 
 * @author zlei.huang@gmail.com 2013-07-12
 * 
 */
public class Log4jManager {
	private HierarchyDynamicMBean hdm;

	public Log4jManager() {
		this(new HierarchyDynamicMBean());
	}

	public Log4jManager(HierarchyDynamicMBean hdm) {
		this.hdm = hdm;
	}

	public List<String> getLoggers() {
		@SuppressWarnings("unchecked")
		Enumeration<Logger> en = LogManager.getCurrentLoggers();

		Logger root = LogManager.getRootLogger();

		LinkedList<String> loggers = new LinkedList<String>();

		if (root != null)
			loggers.add(root.getName());

		while (en.hasMoreElements()) {
			Logger logger = en.nextElement();
			loggers.add(logger.getName());
			// this.hdm.addLoggerMBean(logger.getName());
		}
		return loggers;
	}

	public boolean setLoggerLevel(String name, String level) {
		Logger cat = LogManager.exists(name);

		if (cat == null)
			return false;

		cat.setLevel(Level.toLevel(level));
		return true;
	}

	public String getLoggerLevel(String name) {
		Logger cat = LogManager.exists(name);

		if (cat == null)
			return "no logger for " + name + " found";

		Level level = cat.getLevel();
		if (level == null)
			level = cat.getEffectiveLevel();
		if (level != null)
			return level.toString();
		else
			return "no level setting found";
	}

	public boolean setRootLoggerLevel(String level) {
		Logger root = LogManager.getRootLogger();
		if (root == null)
			return false;

		root.setLevel(Level.toLevel(level));
		return true;
	}

	public String getRootLoggerLevel() {
		Level level = LogManager.getRootLogger().getLevel();
		if (level == null)
			level = LogManager.getRootLogger().getEffectiveLevel();
		if (level != null)
			return level.toString();
		else
			return "no level setting found";
	}

}
