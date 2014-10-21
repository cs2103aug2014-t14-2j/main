package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import userInterface.ezCMessages;
import dataEncapsulation.ActionException;
import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;
import dataEncapsulation.ezC;

public class Finish extends Command {
	
	private static ArrayList<Task> taskList = TotalTaskList.getInstance().getList();

	public Finish(List<Subcommand> commandComponents)
			throws IllegalArgumentException {
		super("finish", commandComponents);
	}

	@Override
	public String execute() throws Exception {
		Task markedTask = markAsCompleted();
		return ezCMessages.getFinishMessage(markedTask);
	}
	
	/* public Task markAsCompleted() throws Exception {
		
		Task taskMarked = searchTaskByName(subcommands); Use Searcher
		taskMarked.setComplete();
		UndoRedoProcessor.undoFinishComponentStack.add(subcommands);
		
		return taskMarked;
	} */
	
	/* public static Task markAsIncomplete() throws Exception {
		
		Task taskMarked = searchTaskByName(subcommands); Use Searcher
		taskMarked.setIncomplete();
		UndoRedoProcessor.undoFinishComponentStack.add(subcommands);
			
		return taskMarked;
	} */

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
