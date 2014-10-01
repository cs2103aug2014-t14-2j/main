package userInterface;

/*
 * This class acts as the interface between the User Interface component
 * and the Main, Data Manipulation, and Power Search components. The other
 * classes within the user interface can be accessed through here.
 */

public class UserInterface {
	
	public static void welcomeUser() {
		ezCMessages.printWelcomeMessage(System.out);
		ezCMessages.printNewLine(System.out);
		ezCMessages.printHelpMessage(System.out);
		return;
	}
}