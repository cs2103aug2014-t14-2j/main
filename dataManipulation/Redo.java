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
import dataEncapsulation.NoResultException;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Redo extends Command {
	
	private String returnMessage;
	
	public Redo(List<Subcommand> commandComponents)
			throws IllegalArgumentException, BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.REDO, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		if(UndoRedoList.getInstance().isRedoStackEmpty()) {
			throw new NoResultException("there is nothing to redo");
		}
		
		Command popped = UndoRedoList.getInstance().popRedoCommand();
		returnMessage = popped.execute();
		UndoRedoList.getInstance().pushUndoCommand(popped);
			
		return returnMessage;
	}

	@Override
	protected void checkValidity() throws BadSubcommandException {
		checkForNoComponents();
	}

	@Override
	public String undo() throws Exception {
		return null;
	}
	
}