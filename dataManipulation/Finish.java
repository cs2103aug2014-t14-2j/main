package dataManipulation;

import java.util.List;

import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;
import dataEncapsulation.ezC;

public class Finish extends Command {

	public Finish(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("finish", commandComponents);
	}

	@Override
	public String execute() {
		Task markedTask = markAsCompleted();
		ezCMessages messages = ezCMessages.getInstance();
		String stringTask = 
	}
	
	public Task markAsCompleted() throws Exception {
		Task taskToBeMarked = searchTaskByName(taskAttributes);
		Task taskMarked = taskToBeMarked;
		taskMarked.setComplete();
		addEditedTask(taskToBeMarked, taskMarked);
		UndoRedoProcessor.undoFinishComponentStack.add(taskAttributes);
		
		return taskMarked;
	}

	@Override
	protected void checkValidity() {
		checkForComponentAmount(1);
		boolean hasTitleComponent =
				checkForSpecificComponent(Subcommand.TYPE.NAME);
		
		if (!hasTitleComponent) {
			throw new IllegalArgumentException("invalid subcommand");
		}
	}

}
