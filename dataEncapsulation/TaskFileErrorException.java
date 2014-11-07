package dataEncapsulation;

//@author A0126720N

import java.util.List;

@SuppressWarnings("serial")
public class TaskFileErrorException extends Exception {
	private List<String> errorList;
	private List<Task> taskList;
	
	public TaskFileErrorException(List<String> errList, List<Task>tkList) {
		super("Warning: Task file error. Skipping corrupt tasks and attempting"
				+ "\n to save corrupt lines to RecoveredTaskFile...");
		errorList = errList;
		taskList = tkList;
	}
	
	public List<String> getErrorList() {
		return errorList;
	}
	
	public List<Task> getTaskList() {
		return taskList;
	}
}
