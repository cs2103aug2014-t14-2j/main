/**
 * @author nellystix
 * 
 * This class processes the undo functions of the program that allows the user to undo 
 * as many commands as has been executed AS LONG AS the program has not been quit. All changes
 * made after quitting the program are final.
 * 
 */

package dataManipulation;

import java.util.List;

import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Date;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;

public class Undo extends Command {
	
	private static TotalTaskList taskList = TotalTaskList.getInstance();
	private static String returnMessage;
	
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
		Command commandToUndo = UndoRedoList.getInstance().peekUndoCommand();
		UndoRedoList.getInstance().pushRedoCommand(UndoRedoList.getInstance().popUndoCommand());
		
		while(commandToUndo.undo() != null) {
			UndoRedoList.getInstance().popUndoCommand();
		}
	}
	
	private void reassignTask(Task toReassign) {
		Date today = new Date();
		if(toReassign.getEndDate().isBefore(today)) {
			taskList.getCompleted().remove(toReassign);
			taskList.getOverdue().add(toReassign);
		}
		else {
			taskList.getCompleted().remove(toReassign);
			taskList.getList().add(toReassign);
		}
	}

	@Override
	public String undo() throws Exception {
		return null;
	}

}
