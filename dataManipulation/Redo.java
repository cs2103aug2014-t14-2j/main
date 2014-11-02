/**
 * 
 * @author nellystix
 * 
 * This class processes the redo functions of the program that allows the user to redo 
 * as many commands as has been executed AS LONG AS the program has not been quit. All changes
 * made after quitting the program are final.
 *
 */

package dataManipulation;

import java.util.List;

import userInterface.CommandHandler;

public class Redo extends Command {
	
	private static String returnMessage;
	
	public Redo(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("redo", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		Command commandToRedo = UndoRedoList.getInstance().peekRedoCommand();
		UndoRedoList.getInstance().pushUndoCommand(UndoRedoList.getInstance().popRedoCommand());
		
		switch(commandToRedo.getType()) {
		
		case "add" :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case "remove" :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case "edit" :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case "finish" :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
	
		default:
			break;
			
		}
	
		return returnMessage;
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}
	
}