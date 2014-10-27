package dataManipulation;

import java.util.List;

import powerSearch.Searcher;
import userInterface.CommandHandler;
import userInterface.ezCMessages;
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
				Task preEditedTask = UndoRedoList.getInstance().popPreEditedTask();
				Command negatedEditCommand = new Edit(Add.dismantleTask(preEditedTask));
				returnMessage = CommandHandler.executeCommand(negatedEditCommand);
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

	@Override
	protected void checkValidity() {
		checkForNoComponents();
	}

}
