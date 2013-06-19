package tk.blizz.ssh.dao;

import tk.blizz.ssh.model.Function;

public interface FunctionDao extends GenericDAO<Function, Long> {
	/**
	 * find a function by it's name
	 * 
	 * @param name
	 * @return
	 */
	Function findByName(String name);

}
