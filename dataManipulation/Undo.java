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
import dataEncapsulation.ActionException;
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
		
		Command commandToUndo = UndoRedoList.getInstance().peekUndoCommand();
		UndoRedoList.getInstance().pushRedoCommand(UndoRedoList.getInstance().popUndoCommand());
		
		switch(commandToUndo.getType()) {
		
			case ADD :
				Command negatedAddCommand = new Remove(commandToUndo.getComponents());
				returnMessage = negatedAddCommand.execute();
				break;
				
			case REMOVE :
				Command negatedRemoveCommand = new Add(commandToUndo.getComponents());
				returnMessage = negatedRemoveCommand.execute();
				break;
				
			case EDIT :
				Command negatedEditCommandRemove = negatedEditRemovePreProcess(commandToUndo);
				Command negatedEditCommandAdd = negatedEditAddPreProcess(commandToUndo);
				negatedEditCommandRemove.execute();
				returnMessage = negatedEditCommandAdd.execute();
				break;
				
			case FINISH :
				List<Task> tasks = Searcher.search(commandToUndo.getComponents(), taskList.getCompleted());
				Task toMarkAsInComplete = tasks.get(0);
				toMarkAsInComplete.setIncomplete();
				reassignTask(toMarkAsInComplete);
				returnMessage = ezCMessages.getInstance().getUnfinishMessage(toMarkAsInComplete);
				break;
				
			default:
				break;
				
		}
		
		return returnMessage;
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

	private Command negatedEditRemovePreProcess(Command commandToUndo) throws Exception {
		Task taskToRemove = getTaskToRemove(commandToUndo.getComponents());
		List<Subcommand> listOfSubC = new Add(subcommands).dismantleTask(taskToRemove);
		Subcommand componentOne = listOfSubC.get(0);
		listOfSubC.clear();
		listOfSubC.add(componentOne);
		Command negatedEditCommandRemove = new Remove(listOfSubC);
		return negatedEditCommandRemove;
	}
	
	private Command negatedEditAddPreProcess(Command commandToUndo) throws Exception {
		Task preEditedTask = UndoRedoList.getInstance().popPreEditedTask();
		List<Subcommand> listOfSubC = new Add(subcommands).dismantleTask(preEditedTask);
		Command negatedEditCommandAdd = new Add(listOfSubC);
		return negatedEditCommandAdd;
	}
	
	private Task getTaskToRemove(List<Subcommand> subC) throws Exception {
		
		List<Task> combinedTaskList = taskList.getAllTasks();
		List<Task> tasks = Searcher.search(subC, combinedTaskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(combinedTaskList, ActionException.ErrorLocation.UNDO, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}
	}

}
