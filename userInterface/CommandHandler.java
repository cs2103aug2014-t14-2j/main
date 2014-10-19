package userInterface;

import globalClasses.EditedPair;
import globalClasses.Task;
import globalClasses.UndoRedoProcessor;
import globalClasses.ezC;

import java.util.List;

import dataManipulation.Command;
import dataManipulation.CommandComponent;
import dataManipulation.DataManipulation;
import dataManipulation.Command.COMMAND_TYPE;

public class CommandHandler {
	
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 * @throws Exception 
	 */
	public static String executeCommand(Command cmd) throws Exception {

		String message = new String(); 
		COMMAND_TYPE type = cmd.getType();
		List<CommandComponent> cc = cmd.getComponents();
		
		switch (type) {
		case ADD:
			Task added = DataManipulation.add(cc);
			message = ezCMessages.getAddMessage(added);
			UndoRedoProcessor.undoCommandStack.add(cmd);
			break;
		case REMOVE:
			Task removed = DataManipulation.remove(cc);
			message = ezCMessages.getDeleteMessage(removed);
			UndoRedoProcessor.undoCommandStack.add(cmd);
			break;
		case EDIT:
			EditedPair edited = DataManipulation.edit(cc);
			message = ezCMessages.getEditMessage(edited.getOld(), edited.getNew());
			UndoRedoProcessor.undoCommandStack.add(cmd);
			break;
		case FINISH: 
			//catch exception: task already completed.
			Task finished = DataManipulation.markAsCompleted(cc);
			message = ezCMessages.getFinishMessage(finished);
			UndoRedoProcessor.undoCommandStack.add(cmd);
			break;
		case COMPLETED: 
			break;
		case SEARCH:
			break;
		case ALL:
			message = ezCMessages.getStringOfTasks(ezC.totalTaskList);
			break;
		case HELP:
			message = ezCMessages.getUserHelpMessage();
		default:
			break;
		}
		return message;
	}

}
