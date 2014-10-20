package globalClasses;

import java.util.ArrayList;
import java.util.List;

import userInterface.CommandHandler;
import userInterface.UserInterface;
import fileIo.FileIo;

public class ezC {
	
	private static UserInterface ui = UserInterface.getInstance();
	
	public static void main(String[] args) {
		FileIo.initializeTaskList(totalTaskList);
		ui.welcomeUser();
		while(true) {
			String feedback = new String();
			try {
				Command command = UserInterface.getUserCommand();
				feedback = CommandHandler.executeCommand(command);
				UndoRedoProcessor.undoCommandStack.add(command);	// Adds the command to the undo command stack
			} catch (IllegalArgumentException e) {
				feedback = UserInterface.getErrorMessage(e);
			} catch (Exception e) {
				feedback = UserInterface.getErrorMessage(e);
			}
			UserInterface.showUser(feedback);
		}
	}
}
