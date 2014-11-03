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

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import userInterface.CommandHandler;

public class Redo extends Command {
	
	private static String returnMessage;
	
	public Redo(List<Subcommand> commandComponents)
			throws IllegalArgumentException, BadCommandException, BadSubcommandException {
		super("redo", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		Command commandToRedo = UndoRedoList.getInstance().peekRedoCommand();
		UndoRedoList.getInstance().pushUndoCommand(UndoRedoList.getInstance().popRedoCommand());
		
		switch(commandToRedo.getType()) {
		
		case ADD :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case REMOVE :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case EDIT :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
			
		case FINISH :
			returnMessage = CommandHandler.executeCommand(commandToRedo);
			break;
	
		default:
			break;
			
		}
	
		return returnMessage;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		checkForNoComponents();
	}
	
}