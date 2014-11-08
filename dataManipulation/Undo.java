/**
 * @author Nelson / A0111014J
 * 
 * This class processes the undo functions of the program that allows the user to undo 
 * as many commands as has been executed AS LONG AS the program has not been quit. All changes
 * made after quitting the program are final.
 * 
 */

package dataManipulation;

import java.util.List;

import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Undo extends Command {
	
	public Undo(List<Subcommand> commandComponents)
			throws IllegalArgumentException, BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.UNDO, commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		if(UndoRedoList.getInstance().isUndoStackEmpty()) {
			throw new Exception("there is nothing to undo");
		}
		
		String response = new String();
		
		do {
			
			Command popped = UndoRedoList.getInstance().popUndoCommand();
			response = popped.undo();
			if(response != null) {
				UndoRedoList.getInstance().pushRedoCommand(popped);
			}
			
		} while(response != null && !UndoRedoList.getInstance().isUndoStackEmpty());
		
		
		if (response == null) {
			throw new Exception("there is nothing to undo");
		} else if (response.isEmpty()){
			throw new Exception("there is nothing to undo");
		}
		
		return response;
		
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
