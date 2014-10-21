

import java.util.ArrayList;
import java.util.List;

import dataManipulation.Command;
import dataManipulation.TotalTaskList;
import userInterface.CommandHandler;
import userInterface.UserInterface;
import fileIo.FileIo;

public class ezC {
	
	private static UserInterface ui = UserInterface.getInstance();
	private static TotalTaskList totalTaskList = TotalTaskList.getInstance();
	private static FileIo fileIo = FileIo.getInstance();
	
	public static void main(String[] args) {
		fileIo.initializeTaskList(totalTaskList.getList());
		ui.welcomeUser();
		while(true) {
			String feedback = new String();
			try {
				Command command = ui.getUserCommand();
				feedback = CommandHandler.executeCommand(command);
				UndoRedoProcessor.undoCommandStack.add(command);	// Adds the command to the undo command stack
			} catch (IllegalArgumentException e) {
				feedback = ui.getErrorMessage(e);
			} catch (Exception e) {
				feedback = ui.getErrorMessage(e);
			}
			ui.showUser(feedback);
		}
	}
}
