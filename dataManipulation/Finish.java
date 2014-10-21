package dataManipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import powerSearch.ExactMatchSearcher;
import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.Task;
import dataEncapsulation.UndoRedoProcessor;
import dataEncapsulation.ezC;
import fileIo.FileIo;

public class Finish extends Command {

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
		Task taskToBeMarked = searchTaskByName(subcommands);
		Task taskMarked = taskToBeMarked;
		taskMarked.setComplete();
		addEditedTask(taskToBeMarked, taskMarked);
		UndoRedoProcessor.undoFinishComponentStack.add(taskAttributes);
		
		return taskMarked;
	}
	
	public static void addEditedTask(Task oldTask, Task newTask) {
		TotalTaskList list = TotalTaskList.getInstance();
		FileIo IoStream = FileIo.getInstance();
		
		list.remove(oldTask);
		list.add(newTask);
		IoStream.rewriteFile();
	}
	
	private static Task searchTaskByName(List<Subcommand> taskAttributes) throws Exception {
		
		TotalTaskList list = TotalTaskList.getInstance();
		List<Task> toEditArray = Searcher.search(taskAttributes, list.getList());
		if(toEditArray.size() > 1) {
			throw new Exception("Too many searches returned, need a more specific task to edit.");
		} else if (toEditArray.size() == 0) {
			throw new Exception("Task Not Found");
		} else {
			Task toEdit = toEditArray.get(0);
			return toEdit;
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
