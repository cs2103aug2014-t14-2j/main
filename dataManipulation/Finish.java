package dataManipulation;

import java.util.List;

import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;

public class Finish extends Command {
	
	private List<Task> taskList = TotalTaskList.getInstance().getList();

	public Finish(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("finish", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		Task markedTask = markAsCompleted();
		ezCMessages messages = ezCMessages.getInstance();
		String stringTask = messages.getFinishMessage(markedTask);
		return stringTask;
	}
	
	public Task markAsCompleted() throws Exception {
		
		List<Task> tasks = Searcher.search(subcommands, taskList);
		if(tasks.size() > 1) {
			ActionException moreThanOne = new ActionException(taskList, ActionException.ErrorLocation.FINISH, subcommands);
			throw moreThanOne;
		}
		else {
			Task taskMarked = tasks.get(0);
			taskMarked.setComplete();
			UndoRedoProcessor.undoCommandStack.add(new Finish(subcommands));
			return taskMarked;
		}
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
