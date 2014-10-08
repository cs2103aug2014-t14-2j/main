package userInterface;

import java.util.List;

import globalClasses.Task;

/**
 * Holds all of the messages associated with the program.
 * 
 * Should not contain any action methods - only access methods
 * @author Natalie Rawle
 *
 */
public class ezCMessages {
	private static final String TAB = "\t";
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final String MESSAGE_WELCOME = "Welcome to ezC. No set-up "
			+ "required.";
	private static final String MESSAGE_HELP = "enter \"exit\" to quit.";
	
	// Task Error Messages
	private static final String ERROR_DUPLICATE_EDIT = "ERROR: There already "
			+ "exists a duplicate task with the edits you are trying to make. "
			+ "No edits were made.";
	private static final String ERROR_TASK_NOT_FOUND = "ERROR: The task you are trying "
			+ "to edit does not exist. No edits were made.";
	private static final String ERROR_DUPLICATE_ADD = "ERROR: There already exists"
			+ " a task you are trying to create. No new task was added.";
	
	public static String getTab() {
		return TAB;
	}
	
	public static String getNewLine() {
		return NEW_LINE;
	}
	
	public static String getWelcomeMessage() {
		return MESSAGE_WELCOME;
	}
	
	public static String getHelpMessage() {
		return MESSAGE_HELP;
	}
	
	public static String getDuplicateEditErrorMessage() {
		return ERROR_DUPLICATE_EDIT;
	}
	
	public static String getTaskNotFoundErrorMessage() {
		return ERROR_TASK_NOT_FOUND;
	}
	
	public static String getDuplicateAddErrorMessage() {
		return ERROR_DUPLICATE_ADD;
	}
	
	public static String getAddMessage(Task newTask) {
		String message = "Task Added:" + NEW_LINE + newTask.toString() + 
				NEW_LINE;
		return message;
	}
	public static String getDeleteMessage(Task deletedTask) {
		String message = "Task deleted: " + NEW_LINE + deletedTask.toString() + 
				NEW_LINE;
		return message;
	}
	public static String getEditMessage(Task original, Task edited) {
		String message = new String();
		return message;
	}
	
	public static String getErrorMessage(Exception e) {
		String opening = "error: ";
		String errorMessage = e.getMessage();
		String totalMessage = opening + errorMessage + NEW_LINE;
		
		return totalMessage;
	}
	
	public static String getStringOfTasks(List<Task> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return "nothing to print" + NEW_LINE;
		}
		
		String allTasks = tasks.get(0).toString();
		
		for (int i = 1; i < tasks.size(); ++i) {
			Task nextTask = tasks.get(i);
			allTasks = allTasks + NEW_LINE + nextTask;
		}
		
		return allTasks;
	}
	
	public static String getFinishMessage(Task completed) {
		String name = completed.getName();
		String totalMessage = name + " was completed" + NEW_LINE;
		return totalMessage;
	}
}