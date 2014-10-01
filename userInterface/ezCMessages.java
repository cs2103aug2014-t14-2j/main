package userInterface;

import java.io.PrintStream;

public class ezCMessages {
	private static final String TAB = "\t";
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	private static final String MESSAGE_WELCOME = "Welcome to ezC. No set-up "
			+ "required.";
	private static final String MESSAGE_HELP = "Display" + TAB + "-d" + TAB + 
			"Displays all tasks.";
	
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
}
