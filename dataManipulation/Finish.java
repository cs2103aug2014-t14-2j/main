package dataManipulation;

import java.util.ArrayList;
import java.util.List;

import powerSearch.Searcher;
import userInterface.ezCMessages;
import dataEncapsulation.BadCommandException;
import dataEncapsulation.BadSubcommandException;
import dataEncapsulation.Task;
import dataManipulation.CommandType.COMMAND_TYPE;
import fileIo.FileIo;

public class Finish extends Command {

	public Finish(List<Subcommand> commandComponents) 
			throws BadCommandException, BadSubcommandException {
		super(COMMAND_TYPE.FINISH, commandComponents);
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
		
		return taskMarked;
	}
	
	public void addEditedTask(Task oldTask, Task newTask) {
		TotalTaskList list = TotalTaskList.getInstance();
		FileIo IoStream = FileIo.getInstance();
		
		list.removeNotCompleted(oldTask);
		list.addCompleted(newTask);
		IoStream.rewriteFile();
	}
	
	private Task searchTaskByName(List<Subcommand> taskAttributes) throws Exception {
		
		List<Task> completedTasks = TotalTaskList.getInstance().getCompleted();
		List<Task> currentTasks = TotalTaskList.getInstance().getList();
		List<Task> overdueTasks = TotalTaskList.getInstance().getOverdue();
		List<List<Task>> categorizedTasks = new ArrayList<List<Task>>();
		categorizedTasks.add(completedTasks);
		categorizedTasks.add(currentTasks);
		categorizedTasks.add(overdueTasks);
		List<Task> toEditArray = new ArrayList<Task>();
		
		for(List<Task> lt : categorizedTasks) {
			toEditArray = Searcher.search(taskAttributes, lt);
			if(toEditArray != null) {
				break;
			}
		}
		
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
	protected void checkValidity() throws BadSubcommandException {
		super.checkValidity();
		checkForNoDuplicateSubcommands();
	}

}
