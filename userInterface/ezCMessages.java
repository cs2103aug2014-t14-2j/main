package userInterface;

import java.util.ArrayList;
import java.util.List;

import dataEncapsulation.Date;
import dataEncapsulation.NoResultException;
import dataEncapsulation.Task;
import dataManipulation.Subcommand;
import dataManipulation.Today;
import dataManipulation.TotalTaskList;

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
	private final String MESSAGE_WELCOME = "Welcome to ezC Task Manager!\n\n";
	
	private final String MESSAGE_DISPLAY_COMMANDS = 
			"Here are a list of available commands (Non case-sensitive) in the format: \n\n"
			+ "(Command) - What the command does\n"
			+ "[subcommand, subcommand, subcommand, ...] (If any)\n\n"
			
			+ "(Add) - Adds a task to the task list.\n"
			+ "[category, location, note, start, end, from, to]\n\n"
			
			+ "(All) - Displays all your tasks pending completion, or have no deadline.\n\n"
			
			+ "(Edit) - Edits a task in your current task list.\n"
			+ "[title, category, location, note, start, end, from, to]\n\n"
			
			+ "(Finish) - Marks a task as completed in your task list.\n\n"
			
			+ "(Redo) - Redoes your previous undo call.\n\n"
			
			+ "(Remove) - Removes a task from the current list of tasks.\n"
			+ "[category, location, note, start, end, from, to]\n\n"
			
			+ "(Repeat) - Repeats a task by a certain frequency.\n"
			+ "[daily, weekly, monthly, annually, once]\n\n"
			
			+ "(Search) - Searches for a task in the task list.\n"
			+ "\n\n"
			
			+ "(Today) - Displays all the tasks for today.\n\n"
			
			+ "(Undo) - Undoes a command that was previously entered.\n\n"
			
			+ "(Unfinish) - Marks a task as uncompleted.\n\n"
			
			+ "Please type \'help <name of command>\' for more information about the command.\n"
			+ "Alternatively, please type \'help list\' to display this list again."; 
	
	private final String ADD_HELP = "Further help for the command: ADD\n\n"
			+ "Command: Add\n"
			+ "Subcommands: category / location / note / start / end / from / to\n\n"
			
			+ "Adds a task to your current list of tasks. "
			+ "This task will not be added if a similar task "
			+ "already exists inside the task list.\n\n"
			
			+ "Note: Subcommands are optional for the command ADD.\n\n"
			
			+ "Format (command) / [subcommand]: \n\n"
			
			+ "(add) \"title of task\" [category] \"title of category\" "
			+ "[location] \"title of location\" [note] \"any additional task notes\" "
			+ "[start] dd/mm/yy [end] dd/mm/yy [from] starttime [to] endtime\n\n"
			
			+ "e.g. add \"Buy chocolate milk\" category \"Groceries\" "
			+ "location \"Big Wig Supermarket\" note \"Milk for cereal\" start 12/3/14 "
			+ "end 12/3/14 from 1pm to 2pm";
	
	private final String ALL_HELP = "Further help for the command: ALL\n\n"
			+ "Command: All\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Displays all the tasks which are either incomplete and/or overdue.\n\n"
			
			+ "Format (command): \n\n"
			
			+ "(all)\n\n"
			
			+ "e.g. all";
	
	private final String EDIT_HELP = "Further help for the command: EDIT\n\n"
			+ "Command: Edit\n"
			+ "Subcommands: title / category / location / note / start / end / from / to\n\n"
			
			+ "Edits a task from your current list of tasks. This task will not be edited if "
			+ "the edited version already exists as a similar task in the task list. "
			+ "If more than one such task exists for editing, a list will be presented "
			+ "for selection.\n\n"
			
			+ "Note: Only one subcommand can be used for the command EDIT.\n\n"
			
			+ "Format (command) / [subcommand]:\n\n"
			
			+ "(edit) \"title of task\" [title] \"new title of task\" "
			+ "OR [category] \"title of category\" OR "
			+ "[location] \"title of location\" OR [note] \"any additional task notes\" "
			+ "OR [start] dd/mm/yy OR [end] dd/mm/yy OR [from] starttime OR [to] endtime\n\n"
			
			+ "e.g. edit \"Buy chocolate milk\" title \"Buy strawberry yoghurt\"\n"
			+ "e.g. edit \"Buy chocolate milk\" location \"Uncle Kumar's Convenience Store\"";
	
	private final String FINISH_HELP = "Further help for the command: FINISH\n\n"
			+ "Command: Finish\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Marks a task, which was previously uncompleted, as completed. "
			+ "This task is then placed into the \"Completed\" list.\n\n"
			
			+ "Note: Only the task's title can be specified.\n\n"
			
			+ "Format [command]:\n\n"
			
			+ "(finish) \"title of task\"\n\n"
			
			+ "e.g. finish \"Buy chocolate milk\"";
	
	private final String REDO_HELP = "Further help for the command: REDO\n\n"
			+ "Command: Redo\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Redoes the previous command that was undone. Does nothing if no commands "
			+ "were undone.\n\n"
			
			+ "Format (command):\n\n"
			
			+ "(redo)\n\n"
			
			+ "e.g. redo";
	
	private final String REMOVE_HELP = "Further help for the command: REMOVE\n\n"
			+ "Command: Remove\n"
			+ "Subcommands: category / location / note / start / end / from / to\n\n"
			
			+ "Removes a task to your current list of tasks. "
			+ "This task will not be removed if there does not exist such a task. "
			+ "If there are similar tasks in the list due to inspecific input, "
			+ "a list of tasks will be displayed for the user to select which "
			+ "exactly they want to delete.\n\n"
			
			+ "Note: Subcommands are optional for the command ADD.\n\n"
			
			+ "Format (command) / [subcommand]: \n\n"
			
			+ "(remove) \"title of task\" [category] \"title of category\" "
			+ "[location] \"title of location\" [note] \"any additional task notes\" "
			+ "[start] dd/mm/yy [end] dd/mm/yy [from] starttime [to] endtime\n\n"
			
			+ "e.g. remove \"Buy chocolate milk\" category \"Groceries\" "
			+ "location \"Big Wig Supermarket\" note \"Milk for cereal\" start 12/3/14 "
			+ "end 12/3/14 from 1pm to 2pm";
	
	private final String REPEAT_HELP = "";
	private final String SEARCH_HELP = "";
	
	private final String TODAY_HELP = "Further help for the command: TODAY\n\n"
			+ "Command: Today\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Displays all the tasks which are either due today, or currently ongoing.\n\n"
			
			+ "Format (command): \n\n"
			
			+ "(today)\n\n"
			
			+ "e.g. today";
	
	private final String UNDO_HELP = "Further help for the command: UNDO\n\n"
			+ "Command: Undo\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Undoes the previous command. Does nothing if no task-changing commands "
			+ "were entered at all.\n\n"
			
			+ "Format (command):\n\n"
			
			+ "(undo)\n\n"
			
			+ "e.g. undo";
	
	private final String UNFINISH_HELP = "Further help for the command: UNFINISH\n\n"
			+ "Command: Unfinish\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Marks a task, which was previously completed, as uncompleted. "
			+ "This task is then placed into the current list of tasks, or the "
			+ "overdue list, depending on the end time of this task.\n\n"
			
			+ "Note: Only the task's title can be specified.\n\n"
			
			+ "Format (command):\n\n"
			
			+ "(unfinish) \"title of task\"\n\n"
			
			+ "e.g. unfinish \"Buy chocolate milk\"";
	
	public String getAddHelp() {
		return ADD_HELP;
	}
	
	public String getAllHelp() {
		return ALL_HELP;
	}
	
	public String getEditHelp() {
		return EDIT_HELP;
	}
	
	public String getFinishHelp() {
		return FINISH_HELP;
	}
	
	public String getRemoveHelp() {
		return REMOVE_HELP;
	}
	
	public String getRepeatHelp() {
		return REPEAT_HELP;
	}
	
	public String getSearchHelp() {
		return SEARCH_HELP;
	}
	
	public String getTodayHelp() {
		return TODAY_HELP;
	}
	
	public String getUndoHelp() {
		return UNDO_HELP;
	}
	
	public String getRedoHelp() {
		return REDO_HELP;
	}
	
	public String getUnfinishHelp() {
		return UNFINISH_HELP;
	}
	
	public String getTab() {
		return TAB;
	}
	
	public String getNewLine() {
		return NEW_LINE;
	}
	
	public String getWelcomeMessage() {
		return MESSAGE_WELCOME;
	}
	
	public String getUserCommandDisplay() {
		return MESSAGE_DISPLAY_COMMANDS;
	} 
	
	public String getAddMessage(Task newTask) {
		String message = "Your task:" + NEW_LINE + NEW_LINE + newTask.toPrint() + 
				NEW_LINE + "has been successfully added.";
		return message;
	}
	public String getDeleteMessage(Task deletedTask) {
		String message = "Your task: " + NEW_LINE + NEW_LINE + deletedTask.toPrint() + 
				NEW_LINE + "has been successfully deleted." + NEW_LINE;
		return message;
	}
	public String getEditMessage(Task original, Task edited) {
		String message = "Your original task: " + NEW_LINE + NEW_LINE + original.toPrint() 
				+ NEW_LINE + "was successfully edited to:" + 
				NEW_LINE + NEW_LINE + edited.toPrint() + NEW_LINE;
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
	
	public String welcomeTaskDisplayForUser() throws Exception {
		List<Task> overdueList = TotalTaskList.getInstance().getOverdue();
		List<Subcommand> dummySubC = new ArrayList<Subcommand>();
		
		if(overdueList.size() == 0) {
			try {
				String todayTasks = new Today(dummySubC).execute();
				String currentTasks = "Your current ongoing task(s) are:" + NEW_LINE + NEW_LINE;
				currentTasks += todayTasks;
				return currentTasks;
			} catch (Exception e){
				return MESSAGE_DISPLAY_COMMANDS;
			}
		}
		else {
			String overdueTasks = "You currently have " + overdueList.size() + " task(s) overdue:" 
					+ NEW_LINE + NEW_LINE;
			String listOfOverdueTasks = getStringOfTasks(overdueList);
			overdueTasks += listOfOverdueTasks;
			
			return overdueTasks;
		}
		
	}
	
	public String makePrintableTask(Task task) {
		String firstLine = task.getName();
		String secondLine = new String();
		Date today = new Date();
		
		String tab = "    ";
		
		firstLine = task.getName();
		
		if (task.hasCategory()) {
			firstLine = firstLine + tab + "Category: " + task.getCategory();
		}
		
		if (!task.getHasDeadline()) {
			if (task.getStartDate().isBefore(today)) {
				firstLine = firstLine + tab + "Started " + task.getStartDate();
			} else {
				firstLine = firstLine + tab + "Starting " + task.getStartDate();
			}
		} else if (task.getStartDate().isEquals(task.getEndDate())) {
			firstLine = firstLine + tab + "On " + task.getStartDate().toPrint();
		} else if (today.isBefore(task.getStartDate())) {
			firstLine = firstLine + tab + "From " + task.getStartDate().toPrint() + " to " + task.getEndDate().toPrint();
		} else {
			firstLine = firstLine + tab + "Due " + task.getEndDate().toPrint();
		}
		
		if (task.hasStartTime()) {
			firstLine = firstLine + tab + task.getStartTime().toString() + " to " + task.getEndTime().toString();
		} else {
			if (!task.getHasDeadline()) {
				firstLine = firstLine + tab + "No Deadline";
			}
		}
		
		if (task.isCompleted()) {
			firstLine = firstLine + tab + "Complete";
		} else {
			firstLine = firstLine + tab + "Incomplete";
		}
		
		if (task.getHasLocation()) {
			secondLine = secondLine + tab + "Location: " + task.getLocation();
		}
		
		if (task.getHasNote()) {
			secondLine = secondLine + tab + "Note: " + task.getNote();
		}
		
		return firstLine + tab + secondLine;
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