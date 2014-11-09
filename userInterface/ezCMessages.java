package userInterface;

import java.util.List;

import dataEncapsulation.Date;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;

/**
 * Holds all of the messages associated with the program.
 * 
 * Should not contain any action methods - only access methods
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
			+ "Edit\n"
			+ "Filter\n"
			+ "Finish\n"
			+ "Help\n"
			+ "Remove\n"
			+ "Search\n"
			+ "Sort\n"
			+ "Today\n"
			+ "Undo\n"
			+ "Unfinish\n"
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
		String message = "Your task:" + NEW_LINE + NEW_LINE + newTask.toPrint() + 
				NEW_LINE + "has been successfully added.";
		return message;
	}
	public String getDeleteMessage(Task deletedTask) {
		String message = "Your task: " + NEW_LINE + NEW_LINE + deletedTask.toPrint() + 
				NEW_LINE + "has been successfully deleted.";
		return message;
	}
	public String getEditMessage(Task original, Task edited) {
		String message = "Your original task: " + NEW_LINE + NEW_LINE + original.toPrint() 
				+ NEW_LINE + "was successfully edited to:" + 
				NEW_LINE + NEW_LINE + edited.toPrint();
		return message;
	}
	public String getUndoEditMessage(Task undidFrom, Task undidTo) {
		String message = "Your task was successfully undone from: " 
				+ NEW_LINE + NEW_LINE + undidFrom.toPrint()
				+ NEW_LINE + "to: " + NEW_LINE + NEW_LINE + undidTo.toPrint();
		return message;
	}
	
	public String getErrorMessage(Exception e) {
		String opening = "ERROR: ";
		String errorMessage = e.getMessage();
		String totalMessage = opening + errorMessage + NEW_LINE;
		
		return totalMessage;
	}
	
	public String getStringOfTasks(List<Task> tasks) throws NoResultException {
		if (tasks == null || tasks.isEmpty()) {
			throw new NoResultException("There are no tasks in this list.");
		}
		
		String allTasks = makePrintableTask(tasks.get(0));
		
		for (int i = 1; i < tasks.size(); ++i) {
			Task nextTask = tasks.get(i);
			String taskToPrint = makePrintableTask(nextTask);
			allTasks = allTasks + NEW_LINE + NEW_LINE + taskToPrint;
		}
		
		return allTasks;
	}
	
	public String makePrintableTask(Task task) {
		String firstLine = task.getName();
		String secondLine = new String();
		Date today = new Date();
		
		firstLine = task.getName();
		
		if (task.hasCategory()) {
			firstLine = firstLine + "\t" + "Category: " + task.getCategory();
		}
		
		if (!task.getHasDeadline()) {
			firstLine = firstLine + "\tStarted " + task.getStartDate();
		} else if (task.getStartDate().isEquals(task.getEndDate())) {
			firstLine = firstLine + "\tOn " + task.getStartDate().toString();
		} else if (today.isBefore(task.getStartDate())) {
			firstLine = firstLine + "\tFrom " + task.getStartDate().toString() + " to " + task.getEndDate().toString();
		} else {
			firstLine = firstLine + "\tDue " + task.getEndDate().toString();
		}
		
		if (task.hasStartTime()) {
			firstLine = firstLine + "\t" + task.getStartTime().toString() + " to " + task.getEndTime().toString();
		} else {
			if (!task.getHasDeadline()) {
				firstLine = firstLine + "\t" + "No Deadline";
			}
		}
		
		if (task.isCompleted()) {
			firstLine = firstLine + "\t" + "Complete";
		} else {
			firstLine = firstLine + "\t" + "Incomplete";
		}
		
		if (task.getHasLocation()) {
			secondLine = secondLine + "\tLocation: " + task.getLocation();
		}
		
		if (task.getHasNote()) {
			secondLine = secondLine + "\tNote: " + task.getNote();
		}
		
		return firstLine + "\t" + secondLine;
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

	public String getNumberedStringOfTasks(List<Task> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return "nothing to print" + NEW_LINE;
		}
		
		String allTasks = "1. " + makePrintableTask(tasks.get(0));
		
		for (int i = 1; i < tasks.size(); ++i) {
			Task nextTask = tasks.get(i);
			int number = i + 1;
			String taskToPrint = number + ". " + makePrintableTask(nextTask);
			allTasks = allTasks + NEW_LINE + NEW_LINE + taskToPrint;
		}
		
		return allTasks;
	}
}