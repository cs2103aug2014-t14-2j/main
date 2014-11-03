package userInterface;

import java.io.PrintStream;
import java.util.Scanner;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandArgException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.Command;

/*
 * This class acts as the interface between the User Interface component
 * and the Main, Data Manipulation, and Power Search components. The other
 * classes within the user interface can be accessed through here.
 */

public class UserInterface {
	private static UserInterface ui;
	private CommandInterpreter ci;
	
	private UserInterface() {
		ci = CommandInterpreter.getInstance();
	};
	
	public static UserInterface getInstance() {
		if (ui == null) {
			ui = new UserInterface();
		}
		
		return ui;
	}
	
	private PrintStream outputStream = System.out;
	private Scanner inputScanner = new Scanner(System.in);
	private ezCMessages messages = ezCMessages.getInstance();
	
	public void showUser(String information) {
		outputStream.print(information);
	}
	
	public void welcomeUser() {
		String userIntro = messages.getWelcomeMessage();
		userIntro = userIntro + messages.getNewLine();
		userIntro = userIntro + messages.getHelpMessage();
		
		outputStream.println(userIntro);
		return;
	}

	public Command getUserCommand() throws BadCommandException, 
		BadSubcommandException, BadSubcommandArgException {
		String input = inputScanner.nextLine();
		
		if (doesUserWantExit(input)) {
			System.exit(0);
		}
		
		Command command = ci.formCommand(input);
		
		return command;
	}
	
	private boolean doesUserWantExit(String input) {
		if (input.equalsIgnoreCase("exit")) {
			return true;
		} else {
			return false;
		}
	}

	public String getErrorMessage(Exception e) {
		return messages.getErrorMessage(e);
	}
	
}