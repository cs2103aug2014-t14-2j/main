package userInterface;

import java.util.List;

import dataEncapsulation.Task;

/**
 * Holds all of the messages associated with the program.
 * 
 * Should not contain any action methods - only access methods
 * @author Natalie Rawle
 *
 */
public class ezCMessages {
	private static ezCMessages messages;
	
	private ezCMessages() {}
	
	public static ezCMessages getInstance() {
		if (messages == null) {
			messages = new ezCMessages();
		}
		
		return messages;
	}
	
	private final String TAB = "\t";
	private final String NEW_LINE = System.getProperty("line.separator");
	
	private final String MESSAGE_WELCOME = "Welcome to ezC. No set-up "
			+ "required.\n";
	private final String MESSAGE_HELP = "Enter \"help\" for a list of commands or \"exit\" to quit.\n";
	private final String MESSAGE_USER_HELP = "List of available commands (Not case sensitive): \n\n"
			+ "Add\n"
			+ "All\n"
			+ "Category\n"
			+ "Complete\n"
			+ "Edit\n"
			+ "Filter\n"
			+ "Finish\n"
			+ "Help\n"
			+ "Note\n"
			+ "Remove\n"
			+ "Search\n"
			+ "Sort\n"
			+ "Today\n"
			+ "Undo\n"
			+ "View\n\n";
	
	// Task Error Messages
	private final String ERROR_DUPLICATE_EDIT = "ERROR: There already "
			+ "exists a duplicate task with the edits you are trying to make. "
			+ "No edits were made.";
	private final String ERROR_TASK_NOT_FOUND = "ERROR: The task you are trying "
			+ "to edit does not exist. No edits were made.";
	private final String ERROR_DUPLICATE_ADD = "ERROR: There already exists"
			+ " a task you are trying to create. No new task was added.";
	
	public String getTab() {
		return TAB;
	}
	
	public String getNewLine() {
		return NEW_LINE;
	}
	
	public String getWelcomeMessage() {
		return MESSAGE_WELCOME;
	}
	
	public String getHelpMessage() {
		return MESSAGE_HELP;
	}
	
	public String getUserHelpMessage() {
		return MESSAGE_USER_HELP;
	}
	
	public String getDuplicateEditErrorMessage() {
		return ERROR_DUPLICATE_EDIT;
	}
	
	public String getTaskNotFoundErrorMessage() {
		return ERROR_TASK_NOT_FOUND;
	}
	
	public String getDuplicateAddErrorMessage() {
		return ERROR_DUPLICATE_ADD;
	}
	
	public String getAddMessage(Task newTask) {
		String message = "Added task:" + NEW_LINE + newTask.toString() + 
				NEW_LINE;
		return message;
	}
	public String getDeleteMessage(Task deletedTask) {
		String message = "Task deleted: " + NEW_LINE + deletedTask.toString() + 
				NEW_LINE;
		return message;
	}
	public String getEditMessage(Task original, Task edited) {
		String message = new String();
		return message;
	}
	
	public String getErrorMessage(Exception e) {
		String opening = "error: ";
		String errorMessage = e.getMessage();
		String totalMessage = opening + errorMessage + NEW_LINE;
		
		return totalMessage;
	}
	
	public String getStringOfTasks(List<Task> tasks) {
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
	
	public String getFinishMessage(Task completed) {
		String name = completed.getName();
		String totalMessage = name + " was completed" + NEW_LINE;
		return totalMessage;
	}
	
	public String getUnfinishMessage(Task incomplete) {
		String name = incomplete.getName();
		String totalMessage = name + " was marked as incomplete" + NEW_LINE;
		return totalMessage;
	}

	public String getChangeDateTypeMessage(String type) {
		String message = "changed date type to " + type;
		return message;
	}
}