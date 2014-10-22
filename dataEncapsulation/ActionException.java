package dataEncapsulation;

import java.util.List;

import dataManipulation.Subcommand;

public class ActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -761775751042015925L;
	
	private List<Task> options;
	private ErrorLocation location;
	private List<Subcommand> subcommands;
	
	public enum ErrorLocation {
		DELETE, EDIT, FINISH, ADD;
		
		public String toString() {
			return this.name().toLowerCase();
		}
	}

	public ActionException(List<Task> taskList, ErrorLocation loc, List<Subcommand> sc) {
		options = taskList;
		location = loc;
		subcommands = sc;
	}
	
	
	public List<Task> getOptions() {
		return options;
	}
	
	public ErrorLocation getLocation() {
		return location;
	}
	
	public List<Subcommand> getSubcommands() {
		return subcommands;
	}

}
