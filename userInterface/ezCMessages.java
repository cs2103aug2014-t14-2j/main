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
			
			+ "( COMMAND ) - What the command does\n"
			+ "[SUBCOMMAND, SUBCOMMAND, SUBCOMMAND, ...] (If any)\n\n"
			
			+ "( Add ) - Adds a task to the task list.\n"
			+ "[category, location, note, start, end, from, to]\n\n"
			
			+ "( All ) - Displays all your tasks pending completion, or have no deadline.\n\n"
			
			+ "( Completed ) - Displays all your tasks which have been completed.\n\n"
			
			+ "( Edit ) - Edits a task in your current task list.\n"
			+ "[title, category, location, note, start, end, from, to]\n\n"
			
			+ "( Finish ) - Marks a task as completed in your task list.\n\n"
			
			+ "( Redo ) - Redoes your previous undo call.\n\n"
			
			+ "( Remove ) - Removes a task from the current list of tasks.\n"
			+ "[category, location, note, start, end, from, to]\n\n"
			
			+ "( Repeat ) - Repeats a task by a certain frequency.\n"
			+ "[daily, weekly, monthly, annually, once]\n\n"
			
			+ "( Search ) - Searches for a task in the task list.\n"
			+ "\n\n"
			
			+ "( Today ) - Displays all the tasks for today.\n\n"
			
			+ "( Undo ) - Undoes a command that was previously entered.\n\n"
			
			+ "( Unfinish ) - Marks a task as uncompleted.\n\n"
			
			+ "Please type \'help <name of command>\' for more information about the command.\n"
			+ "Alternatively, please type \'help\' to display this list again."; 
	
	private final String ADD_HELP = "Further help for the command: ADD\n\n"
			
			+ "Command: Add\n"
			+ "Subcommands: category / location / note / start / end / from / to\n\n"
			
			+ "Adds a task to your current list of tasks. "
			+ "This task will not be added if a similar task "
			+ "already exists inside the task list.\n\n"
			
			+ "Note: Subcommands are optional for the command ADD.\n\n"
			
			+ "Format ( COMMAND ) / [SUBCOMMAND]: \n\n"
			
			+ "( add ) \"title of task\" [category] \"title of category\" "
			+ "[location] \"title of location\" [note] \"any additional task notes\" "
			+ "[start] dd/mm/yy [end] dd/mm/yy [from] starttime [to] endtime\n\n"
			
			+ "e.g. add \"Buy chocolate milk\" category \"Groceries\" "
			+ "location \"Big Wig Supermarket\" note \"Milk for cereal\" start 12/3/14 "
			+ "end 12/3/14 from 1pm to 2pm";
	
	private final String ALL_HELP = "Further help for the command: ALL\n\n"
			
			+ "Command: All\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Displays all the tasks which are either incomplete and/or overdue.\n\n"
			
			+ "Format ( COMMAND ): \n\n"
			
			+ "( all )\n\n"
			
			+ "e.g. all";
	
	private final String EDIT_HELP = "Further help for the command: EDIT\n\n"
			
			+ "Command: Edit\n"
			+ "Subcommands: title / category / location / note / start / end / from / to\n\n"
			
			+ "Edits a task from your current list of tasks. This task will not be edited if "
			+ "the edited version already exists as a similar task in the task list. "
			+ "If more than one such task exists for editing, a list will be presented "
			+ "for selection.\n\n"
			
			+ "Note: Only one subcommand can be used for the command EDIT.\n\n"
			
			+ "Format ( COMMAND ) / [SUBCOMMAND]:\n\n"
			
			+ "( edit ) \"title of task\" [title] \"new title of task\" "
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
			
			+ "Format [ COMMAND ]:\n\n"
			
			+ "( finish ) \"title of task\"\n\n"
			
			+ "e.g. finish \"Buy chocolate milk\"";
	
	private final String REDO_HELP = "Further help for the command: REDO\n\n"
			
			+ "Command: Redo\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Redoes the previous command that was undone. Does nothing if no commands "
			+ "were undone.\n\n"
			
			+ "Format ( COMMAND ):\n\n"
			
			+ "( redo )\n\n"
			
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
			
			+ "Format ( COMMAND ) / [SUBCOMMAND]: \n\n"
			
			+ "( remove ) \"title of task\" [category] \"title of category\" "
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
			
			+ "Format ( COMMAND ): \n\n"
			
			+ "( today )\n\n"
			
			+ "e.g. today";
	
	private final String UNDO_HELP = "Further help for the command: UNDO\n\n"
			
			+ "Command: Undo\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Undoes the previous command. Does nothing if no task-changing commands "
			+ "were entered at all.\n\n"
			
			+ "Format ( COMMAND ):\n\n"
			
			+ "( undo )\n\n"
			
			+ "e.g. undo";
	
	private final String UNFINISH_HELP = "Further help for the command: UNFINISH\n\n"
			
			+ "Command: Unfinish\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Marks a task, which was previously completed, as uncompleted. "
			+ "This task is then placed into the current list of tasks, or the "
			+ "overdue list, depending on the end time of this task.\n\n"
			
			+ "Note: Only the task's title can be specified.\n\n"
			
			+ "Format ( COMMAND ):\n\n"
			
			+ "( unfinish ) \"title of task\"\n\n"
			
			+ "e.g. unfinish \"Buy chocolate milk\"";
	
	private final String DATE_HELP = "Further help for dates:\n\n"
			
			+ "Commands related to date: Change date type <m/d> OR <d/m>\n"
			+ "Subcommands related to date: start / end\n\n"
			
			+ "Format ( COMMAND ) / [SUBCOMMAND]:\n\n"
			
			+ "This command allows you to change the date format between the middle-endian "
			+ "format (mm/dd/yy) and the little-endian format (dd/mm/yy)."
			
			+ "( change date type ) m/d\n\n"
			
			+ "e.g. change date type m/d\n"
			+ "e.g. change date type d/m\n\n"
			
			+ "[start]: It is the subcommand to indicate the date that you wish to have "
			+ "the task start from. If this date is not stated, the task will be created "
			+ "based on today's date instead.\n\n"
			
			+ "e.g. start 6/12/14\n"
			+ "e.g. start tomorrow\n"
			+ "e.g. start today\n"
			+ "e.g. start wednesday\n"
			
			+ "[end]: It is the subcommand to indicate the date that you would like the "
			+ "task to end on. Any time past this date would have the task shift into the "
			+ "overdue task list.\n\n"
			
			+ "e.g. end 7/12/14\n"
			+ "e.g. end today\n"
			+ "e.g. end tomorrow\n"
			+ "e.g. end sunday";
	
	private final String TIME_HELP = "Further help for time:\n\n"
			
			+ "Subcommands related to time: from / to\n\n"
			
			+ "NOTE: For format of use, please refer to ADD / EDIT / REPEAT / SEARCH / REMOVE\n\n"
			
			+ "Format [SUBCOMMAND]:\n\n"
			
			+ "[from]: It is the subcommand to indicate the time which the task will commence from.\n\n"
			
			+ "e.g. from 1pm\n"
			+ "e.g. from 3:00am\n"
			+ "e.g. from 05:00\n"
			+ "e.g. from 1700\n\n"
			
			+ "[end]: It is the subcommand to indicate the time which the task will end at, "
			+ "after which the task will be moved to the overdue task list.\n\n"
			
			+ "e.g. end 2am\n"
			+ "e.g. end 5:00pm\n"
			+ "e.g. end 20:00\n"
			+ "e.g. end 0600";
	
	private final String COMPLETED_HELP = "Further help for the command: COMPLETE / COMPLETED\n\n"
			
			+ "Command: Completed\n"
			+ "Subcommands: NIL\n\n"
			
			+ "Displays all the tasks that have ever been completed.\n\n"
			
			+ "Format ( COMMAND ):\n\n"
			
			+ "( completed )\n\n"
			
			+ "e.g. completed";
	
	public final String ALL_TASKS_LIST_MESSAGE = "[ ALL-TASKS LIST ]:\n\n"
			
			+ "All current on-going task(s):" + NEW_LINE + NEW_LINE;
	
	public final String OVERDUE_TASKS_LIST_MESSAGE = "[ OVERDUE-TASKS LIST ]:\n\n"
			
			+ "All current overdue task(s):" + NEW_LINE + NEW_LINE;
	
	public final String TODAY_TASKS_LIST_MESSAGE = "[ TODAY'S TASKS LIST ]:\n\n"
			
			+ "All task(s) that are on-going for today:" + NEW_LINE + NEW_LINE;
	
	public final String COMPLETED_TASKS_LIST_MESSAGE = "[ COMPLETED TASKS LIST ]:\n\n"
			
			+ "All task(s) that have been completed up till date:" + NEW_LINE + NEW_LINE;
	
	public String getAddHelp() {
		return ADD_HELP;
	}
	
	public String getAllHelp() {
		return ALL_HELP;
	}
	
	public String getDateHelp() {
		return DATE_HELP;
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
	
	public String getTimeHelp() {
		return TIME_HELP;
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
	
	public String getUserCommandDisplay() {
		return MESSAGE_DISPLAY_COMMANDS;
	}
	
	public String getWelcomeMessage() {
		return MESSAGE_WELCOME;
	}
	
	public String getAllTasksListMessage() {
		return ALL_TASKS_LIST_MESSAGE;
	}
	
	public String getOverdueTasksListMessage() {
		return OVERDUE_TASKS_LIST_MESSAGE;
	}
	
	public String getTodayTasksListMessage() {
		return TODAY_TASKS_LIST_MESSAGE;
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
	public String getUndoRepeatMessage(Task taskUnrepeated) {
		String message = "Your task: " + NEW_LINE + NEW_LINE + taskUnrepeated.toPrint() +
				NEW_LINE +  "has been successfully unrepeated.";
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