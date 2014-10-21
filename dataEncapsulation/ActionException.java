package dataEncapsulation;

import java.util.List;

public class ActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -761775751042015925L;
	
	private List<Task> options;
	private ErrorLocation location;
	
	public enum ErrorLocation {
		DELETE, EDIT;
		
		public String toString() {
			return this.name().toLowerCase();
		}
	}

	public ActionException(List<Task> taskList, ErrorLocation loc) {
		options = taskList;
		location = loc;
	}
	
	
	public List<Task> getOptions() {
		return options;
	}
	
	public ErrorLocation getLocation() {
		return location;
	}

}
