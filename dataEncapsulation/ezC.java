package dataEncapsulation;

import userInterface.CommandHandler;
import userInterface.UserInterface;
import dataManipulation.Command;
import dataManipulation.TotalTaskList;
import dataManipulation.UndoRedoList;
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
				UndoRedoList.getInstance().pushUndoCommand(command);	// Adds the command to the undo command stack
			} catch (IllegalArgumentException e) {
				feedback = ui.getErrorMessage(e);
			} catch (Exception e) {
				feedback = ui.getErrorMessage(e);
			}
			ui.showUser(feedback);
		}
	}
}
