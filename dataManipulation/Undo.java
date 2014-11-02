/**
 * @author nellystix
 */

package dataManipulation;

import java.util.List;

import powerSearch.Searcher;
import userInterface.CommandHandler;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;

public class Undo extends Command {
	
	private static List<Task> taskList = TotalTaskList.getInstance().getList();
	private static String returnMessage;
	
	public Undo(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("undo", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		
		Command commandToUndo = UndoRedoList.getInstance().peekUndoCommand();
		UndoRedoList.getInstance().pushRedoCommand(UndoRedoList.getInstance().popUndoCommand());
		
		switch(commandToUndo.getType()) {
		
			case "add" :
				Command negatedAddCommand = new Remove(commandToUndo.getComponents());
				returnMessage = CommandHandler.executeCommand(negatedAddCommand);
				break;
				
			case "remove" :
				Command negatedRemoveCommand = new Add(commandToUndo.getComponents());
				returnMessage = CommandHandler.executeCommand(negatedRemoveCommand);
				break;
				
			case "edit" :
				Command negatedEditCommandRemove = negatedEditRemovePreProcess(commandToUndo);
				Command negatedEditCommandAdd = negatedEditAddPreProcess(commandToUndo);
				CommandHandler.executeCommand(negatedEditCommandRemove);
				returnMessage = CommandHandler.executeCommand(negatedEditCommandAdd);
				break;
				
			case "finish" :
				List<Task> tasks = Searcher.search(commandToUndo.getComponents(), taskList);
				Task toMarkAsInComplete = tasks.get(0);
				toMarkAsInComplete.setIncomplete();
				returnMessage = ezCMessages.getInstance().getUnfinishMessage(toMarkAsInComplete);
				break;
				
			default:
				break;
				
		}
		
		return returnMessage;
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
		
		List<Task> tasks = Searcher.search(subC, taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.UNDO, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}
	}

}
