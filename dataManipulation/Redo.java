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
import dataManipulation.CommandType.COMMAND_TYPE;

public class Redo extends Command {
	
	private static String returnMessage;
	
	public Redo(List<Subcommand> commandComponents)
			throws IllegalArgumentException, BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REDO, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		Command commandToRedo = UndoRedoList.getInstance().peekRedoCommand();
		UndoRedoList.getInstance().pushUndoCommand(UndoRedoList.getInstance().popRedoCommand());
		
		switch(commandToRedo.getType()) {
		
		case ADD :
			returnMessage = commandToRedo.execute();
			break;
			
		case REMOVE :
			returnMessage = commandToRedo.execute();
			break;
			
		case EDIT :
			returnMessage = commandToRedo.execute();
			break;
			
		case FINISH :
			returnMessage = commandToRedo.execute();
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

	@Override
	public String undo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}