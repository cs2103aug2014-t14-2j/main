package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import powerSearch.Searcher;
import userInterface.CommandHandler;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;

public class Undo extends Command {
	
	private List<Subcommand> dummySubcommands = new ArrayList<Subcommand>();
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
				Task preEditedTask = UndoRedoList.getInstance().popPreEditedTask();
				Task taskToRemove = getTaskToRemove();
				Command negatedEditCommandRemove = new Remove(new Add(dummySubcommands).dismantleTask(taskToRemove));
				Command negatedEditCommandAdd = new Add(new Add(dummySubcommands).dismantleTask(preEditedTask));
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
	
	private Task getTaskToRemove() throws Exception {
		
		List<Task> tasks = Searcher.search(subcommands, taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.UNDO, subcommands);
			throw moreThanOne;
		}
		else {
			return tasks.get(0);
		}
	}

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
