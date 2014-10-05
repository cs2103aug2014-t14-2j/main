package userInterface;

import globalClasses.Command;
import globalClasses.Command.COMMAND_TYPE;
import globalClasses.CommandComponent;
import globalClasses.Date;
import globalClasses.Task;
import globalClasses.ezC;
import fileIo.FileIo;

import java.util.List;

import powerSearch.ExactMatchSearcher;
import dataManipulation.dataManipulation;

public class CommandHandler {
	
	/**
	 * Will call the appropriate method from data manipulation or search
	 * based on the command type
	 * @param command
	 * @return a string containing the feedback returned by the called method
	 */
	public static String executeCommand(Command cmd) {

		COMMAND_TYPE type = cmd.getType();
		List<CommandComponent> cc = cmd.getComponents();
		
		switch (type) {
		case ADD:
			TaskAdder.doAddTask(cc);
			break;
		case REMOVE:
			dataManipulation.remove(cc);
			break;
		case EDIT:
			TaskEditor.doEditTask(cc);
			break;
		case SEARCH:
			break;
		default:
			break;
		}
	}

}
