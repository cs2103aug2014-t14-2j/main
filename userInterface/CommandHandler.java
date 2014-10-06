package userInterface;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;
import globalClasses.Date;
import globalClasses.EditedPair;
import globalClasses.Task;
import globalClasses.ezC;
import fileIo.FileIo;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import dataManipulation.DataManipulation;

public class CommandHandler {
	
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 */
	public static String executeCommand(Command cmd) {

		String message; 
		COMMAND_TYPE type = cmd.getType();
		List<CommandComponent> cc = cmd.getComponents();
		
		switch (type) {
		case ADD:
			Task added = DataManipulation.add(cc);
			message = ezCMessages.getAddMessage(added);
			break;
		case REMOVE:
			Task removed = DataManipulation.remove(cc);
			message = ezCMessages.getDeleteMessage(removed);
		case EDIT:
			 EditedPair edited = DataManipulation.edit(cc);
			 message = ezCMessages.getEditMessage(edited.getOld(), edited.getNew());
			break;
		case SEARCH:
			break;
		default:
			break;
		}
		return message;
	}

}
