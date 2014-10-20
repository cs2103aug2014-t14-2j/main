package userInterface;

import java.io.PrintStream;
import java.util.Scanner;

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
	
	public void showUser(String information) {
		outputStream.print(information);
	}
	
	public void welcomeUser() {
		String userIntro = ezCMessages.getWelcomeMessage();
		userIntro = userIntro + ezCMessages.getNewLine();
		userIntro = userIntro + ezCMessages.getHelpMessage();
		
		outputStream.println(userIntro);
		return;
	}

	public Command getUserCommand() {
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
		return ezCMessages.getErrorMessage(e);
	}
	
}