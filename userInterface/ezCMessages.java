package userInterface;

import java.io.PrintStream;

public class ezCMessages {
	private static final String TAB = "\t";
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final String MESSAGE_WELCOME = "Welcome to ezC. No set-up "
			+ "required.";
	private static final String MESSAGE_HELP = "Display" + TAB + "-d" + TAB + 
			"Displays all tasks.";
	
	// Task Error Messages
	private static final String duplicateEditTask = "ERROR: There already exists a duplicate task with the edits you are trying to make. No edits were made.";
	private static final String taskNotFound = "ERROR: The task you are trying to edit does not exist. No edits were made.";
	private static final String duplicateAddTask = "ERROR: There already exists a task you are trying to create. No new task was added.";
	
	public static String getWelcomeMessage() {
		return MESSAGE_WELCOME;
	}
	
	public static String getHelpMessage() {
		return MESSAGE_HELP;
	}
	
	public static void printWelcomeMessage(PrintStream os) {
		os.print(MESSAGE_WELCOME);
	}
	
	public static void printHelpMessage(PrintStream os) {
		os.print(MESSAGE_HELP);
	}
	
	public static void printNewLine(PrintStream os) {
		os.print(NEW_LINE);
	}
	
	public static void printErrorMessage(String typeOfError) {
		PrintStream os = null;
		if(typeOfError.equals("duplicate edit task")) {
			os.print(duplicateEditTask);
			os.close();
		}
		else if(typeOfError.equals("404")) {
			os.print(taskNotFound);
			os.close();
		}
		else if(typeOfError.equals("duplicate add task")) {
			os.print(duplicateAddTask);
			os.close();
		}
	}
	
	public static void printConfirmEdited(String newEditedTask) {
		PrintStream os = null;
		os.print("Your task " + newEditedTask + " has been edited.");
		os.close();
	}
	
	public static void printConfirmAdded(String newAddedTask) {
		PrintStream os = null;
		os.print("A new task " + newAddedTask + " has been added.");
	}
	
}